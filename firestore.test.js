const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_student_1";
const BOB_UID = "bob_student_2";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: denied all reads and writes", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("favorites").get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("notes").get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("quizResults").get());

  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("notes").doc("note_1").set({
      userId: ALICE_UID,
      title: "قانون كولوم",
      body: "القوة تتناسب طرديا مع حاصل ضرب الشحنتين",
      chapterNumber: 1,
      category: "LAW",
      createdAt: new Date(Date.now() - 1000),
      updatedAt: new Date(Date.now() - 1000),
    })
  );
});

test("Favorites: owner can create, list, update optional note, and delete; ghost fields and cross-user denied", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const now = new Date(Date.now() - 1000);

  const favRef = aliceDb.collection("users").doc(ALICE_UID).collection("favorites").doc("ch1_def_1");
  await assertSucceeds(
    favRef.set({
      userId: ALICE_UID,
      itemId: "ch1_def_1",
      itemType: "DEFINITION",
      chapterNumber: 1,
      title: "الكهربائية الساكنة",
      content: "تجمع الشحنات الكهربائية على سطوح الأجسام المعزولة.",
      createdAt: now,
      updatedAt: now,
    })
  );

  // List own favorites
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("favorites").get()
  );

  // Reject ghost 'id' field (DocumentId collision guard)
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("favorites").doc("ch1_def_2").set({
      id: "ch1_def_2",
      userId: ALICE_UID,
      itemId: "ch1_def_2",
      itemType: "DEFINITION",
      chapterNumber: 1,
      title: "الكشاف الكهربائي",
      content: "جهاز يستعمل في تجارب الكهربائية الساكنة.",
      createdAt: now,
      updatedAt: now,
    })
  );

  // Reject future timestamp
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("favorites").doc("ch1_def_3").set({
      userId: ALICE_UID,
      itemId: "ch1_def_3",
      itemType: "LAW",
      chapterNumber: 1,
      title: "قانون كولوم",
      content: "F = k q1 q2 / r^2",
      createdAt: new Date(Date.now() + 60000),
      updatedAt: now,
    })
  );

  // Cross-user access denied
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("favorites").doc("ch1_def_1").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("favorites").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("favorites").doc("ch1_def_1").delete());

  // Owner delete succeeds
  await assertSucceeds(favRef.delete());
});

test("StudyNotes: owner can create, update mutable fields, list, and delete; immutable createdAt protected", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = new Date(Date.now() - 5000);

  const noteRef = aliceDb.collection("users").doc(ALICE_UID).collection("notes").doc("note_1");
  await assertSucceeds(
    noteRef.set({
      userId: ALICE_UID,
      title: "ملاحظة التوالي والتوازي",
      body: "في التوالي التيار متساوي وفي التوازي فرق الجهد متساوي",
      chapterNumber: 3,
      category: "SUMMARY",
      createdAt: created,
      updatedAt: created,
    })
  );

  await assertSucceeds(
    noteRef.update({
      title: "ربط المقاومات - مهمة وزارية",
      body: "في ربط التوازي يكون فرق الجهد متساوياً عبر كل مقاومة.",
      chapterNumber: 3,
      category: "EXAM_TIP",
      updatedAt: new Date(Date.now() - 1000),
    })
  );

  // Reject modifying createdAt
  await assertFails(
    noteRef.update({
      createdAt: new Date(Date.now() - 500),
      updatedAt: new Date(Date.now() - 500),
    })
  );

  // Reject cross-user update
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).collection("notes").doc("note_1").update({
      title: "Hacked",
      updatedAt: new Date(Date.now() - 1000),
    })
  );

  await assertSucceeds(noteRef.delete());
});

test("QuizResults: owner can create and list; updates rejected; out-of-bounds score rejected", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const now = new Date(Date.now() - 1000);

  const quizRef = aliceDb.collection("users").doc(ALICE_UID).collection("quizResults").doc("quiz_1");
  await assertSucceeds(
    quizRef.set({
      userId: ALICE_UID,
      quizTitle: "اختبار الفصل الأول: الكهربائية الساكنة",
      chapterNumber: 1,
      score: 9,
      totalQuestions: 10,
      durationSeconds: 185,
      createdAt: now,
    })
  );

  // Reject score > totalQuestions
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("quizResults").doc("quiz_bad").set({
      userId: ALICE_UID,
      quizTitle: "اختبار",
      chapterNumber: 1,
      score: 12,
      totalQuestions: 10,
      durationSeconds: 100,
      createdAt: now,
    })
  );

  // Reject updates to quiz results
  await assertFails(quizRef.update({ score: 10 }));

  // Reject cross-user read
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("quizResults").get());
});
