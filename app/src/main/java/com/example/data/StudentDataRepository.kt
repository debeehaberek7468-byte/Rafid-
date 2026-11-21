package com.example.data

import android.content.Context
import com.example.R
import com.example.model.FavoriteItem
import com.example.model.FavoriteType
import com.example.model.NoteCategory
import com.example.model.QuizResult
import com.example.model.StudyNote
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StudentDataRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private fun requireUserId(): String =
        auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")

    fun observeFavorites(userId: String): Flow<List<FavoriteItem>> = flow {
        val uid = requireUserId()
        check(uid == userId) { "Signed-in user mismatch." }
        val path = "users/$uid/favorites"
        emitAll(
            db.collection("users").document(uid).collection("favorites")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(
                        FavoriteItem::class.java,
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )
                }
                .catch { error ->
                    logStreamFailure(error, OperationType.LIST, path, uid)
                    throw error
                }
        )
    }

    fun observeNotes(userId: String): Flow<List<StudyNote>> = flow {
        val uid = requireUserId()
        check(uid == userId) { "Signed-in user mismatch." }
        val path = "users/$uid/notes"
        emitAll(
            db.collection("users").document(uid).collection("notes")
                .orderBy("updatedAt", Query.Direction.DESCENDING)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(
                        StudyNote::class.java,
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )
                }
                .catch { error ->
                    logStreamFailure(error, OperationType.LIST, path, uid)
                    throw error
                }
        )
    }

    fun observeQuizResults(userId: String): Flow<List<QuizResult>> = flow {
        val uid = requireUserId()
        check(uid == userId) { "Signed-in user mismatch." }
        val path = "users/$uid/quizResults"
        emitAll(
            db.collection("users").document(uid).collection("quizResults")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(
                        QuizResult::class.java,
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )
                }
                .catch { error ->
                    logStreamFailure(error, OperationType.LIST, path, uid)
                    throw error
                }
        )
    }

    suspend fun addFavorite(
        itemId: String,
        type: FavoriteType,
        chapterNumber: Int,
        title: String,
        content: String,
        extraNote: String? = null
    ): Result<Unit> {
        var docPath = "users/${auth.currentUser?.uid ?: "unauthenticated"}/favorites/$itemId"
        return try {
            val uid = requireUserId()
            require(itemId.isNotBlank() && itemId.length <= 128) { "Invalid itemId" }
            val safeChapter = chapterNumber.coerceIn(1, 9)
            val safeTitle = title.trim().take(200)
            val safeContent = content.trim().take(2000)
            require(safeTitle.isNotEmpty() && safeContent.isNotEmpty()) { "Title and content required" }

            val docRef = db.collection("users").document(uid)
                .collection("favorites").document(itemId)
            docPath = docRef.path

            val payload = mapOf(
                "userId" to uid,
                "itemId" to itemId,
                "itemType" to type.name,
                "chapterNumber" to safeChapter,
                "title" to safeTitle,
                "content" to safeContent,
                "extraNote" to extraNote?.trim()?.take(500)?.takeIf { it.isNotEmpty() },
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            ).filterValues { it != null }

            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun removeFavorite(itemId: String): Result<Unit> {
        var docPath = "users/${auth.currentUser?.uid ?: "unauthenticated"}/favorites/$itemId"
        return try {
            val uid = requireUserId()
            require(itemId.isNotBlank()) { "itemId must not be blank" }
            val docRef = db.collection("users").document(uid)
                .collection("favorites").document(itemId)
            docPath = docRef.path
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docPath)
            Result.failure(e)
        }
    }

    suspend fun addNote(
        title: String,
        body: String,
        chapterNumber: Int,
        category: NoteCategory
    ): Result<Unit> {
        var docPath = "users/${auth.currentUser?.uid ?: "unauthenticated"}/notes"
        return try {
            val uid = requireUserId()
            val safeTitle = title.trim().take(120)
            val safeBody = body.trim().take(2000)
            require(safeTitle.isNotEmpty()) { "عنوان الملاحظة مطلوب" }
            require(safeBody.isNotEmpty()) { "محتوى الملاحظة مطلوب" }
            val safeChapter = chapterNumber.coerceIn(0, 9)

            val docRef = db.collection("users").document(uid)
                .collection("notes").document()
            docPath = docRef.path

            val payload = mapOf(
                "userId" to uid,
                "title" to safeTitle,
                "body" to safeBody,
                "chapterNumber" to safeChapter,
                "category" to category.name,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun updateNote(
        noteId: String,
        title: String,
        body: String,
        chapterNumber: Int,
        category: NoteCategory
    ): Result<Unit> {
        var docPath = "users/${auth.currentUser?.uid ?: "unauthenticated"}/notes/$noteId"
        return try {
            val uid = requireUserId()
            require(noteId.isNotBlank()) { "Note ID must not be blank" }
            val safeTitle = title.trim().take(120)
            val safeBody = body.trim().take(2000)
            require(safeTitle.isNotEmpty()) { "عنوان الملاحظة مطلوب" }
            require(safeBody.isNotEmpty()) { "محتوى الملاحظة مطلوب" }
            val safeChapter = chapterNumber.coerceIn(0, 9)

            val docRef = db.collection("users").document(uid)
                .collection("notes").document(noteId)
            docPath = docRef.path

            docRef.update(
                mapOf(
                    "title" to safeTitle,
                    "body" to safeBody,
                    "chapterNumber" to safeChapter,
                    "category" to category.name,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun deleteNote(noteId: String): Result<Unit> {
        var docPath = "users/${auth.currentUser?.uid ?: "unauthenticated"}/notes/$noteId"
        return try {
            val uid = requireUserId()
            require(noteId.isNotBlank()) { "Note ID must not be blank" }
            val docRef = db.collection("users").document(uid)
                .collection("notes").document(noteId)
            docPath = docRef.path
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docPath)
            Result.failure(e)
        }
    }

    suspend fun saveQuizResult(
        quizTitle: String,
        chapterNumber: Int,
        score: Int,
        totalQuestions: Int,
        durationSeconds: Int
    ): Result<Unit> {
        var docPath = "users/${auth.currentUser?.uid ?: "unauthenticated"}/quizResults"
        return try {
            val uid = requireUserId()
            val safeTotal = totalQuestions.coerceIn(1, 100)
            val safeScore = score.coerceIn(0, safeTotal)
            val safeDuration = durationSeconds.coerceIn(0, 7200)
            val safeChapter = chapterNumber.coerceIn(0, 9)
            val safeTitle = quizTitle.trim().take(150).ifEmpty { "اختبار فيزياء" }

            val docRef = db.collection("users").document(uid)
                .collection("quizResults").document()
            docPath = docRef.path

            val payload = mapOf(
                "userId" to uid,
                "quizTitle" to safeTitle,
                "chapterNumber" to safeChapter,
                "score" to safeScore,
                "totalQuestions" to safeTotal,
                "durationSeconds" to safeDuration,
                "createdAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    private fun logStreamFailure(
        error: Throwable,
        op: OperationType,
        path: String,
        uid: String
    ) {
        val firestoreError = generateSequence(error) { it.cause }
            .filterIsInstance<FirebaseFirestoreException>()
            .firstOrNull()
        if (firestoreError == null && error is CancellationException) return
        val exceptionToLog = firestoreError ?: (error as? Exception)
        if (exceptionToLog != null && auth.currentUser?.uid == uid) {
            handleFirestoreError(exceptionToLog, op, path)
        }
    }
}
