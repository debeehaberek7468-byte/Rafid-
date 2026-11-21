package com.example.data

import com.example.model.FavoriteType
import com.example.model.LawSimulatorType
import com.example.model.MinisterialTip
import com.example.model.PhysicsChapter
import com.example.model.PhysicsLaw
import com.example.model.QuizQuestion
import com.example.model.SearchResultItem
import java.util.Locale
import kotlin.math.abs

data class SimulationResult(
    val primaryValue: Double,
    val formattedAnswer: String,
    val steps: List<String>
)

object CurriculumRepository {
    val chapters: List<PhysicsChapter> = chaptersPart1 + chaptersPart2

    val allLaws: List<PhysicsLaw> = chapters.flatMap { it.laws }

    val ministerialTips: List<MinisterialTip> = listOf(
        MinisterialTip(
            id = "tip_1",
            title = "ابدأ بقراءة الأسئلة بهدوء وترتيب الدفتر الامتحاني",
            category = "تنظيم الدفتر",
            details = "خصص صفحة لكل سؤال رئيسي وضع رقم السؤال والفرع بوضوح في أعلى الصفحة. اكتب بخط واضح ومرتب واترك سطراً فارغاً بين كل خطوة وأخرى، فالمصحح الوزاري يُقدّر الدفتر المنظم."
        ),
        MinisterialTip(
            id = "tip_2",
            title = "ثلاثية حل المسألة المضمونة (المعطيات ← القانون ← الوحدات)",
            category = "حل المسائل",
            details = "قبل البدء بالضرب والقسمة، اكتب المعطيات على اليمين وتأكد من تحويل الوحدات: السنتيمتر إلى متر (×10⁻²)، المايكروكولوم إلى كولوم (×10⁻⁶)، والدقيقة إلى ثانية (×60). ولا تنسَ وحدة القياس في الناتج النهائي!"
        ),
        MinisterialTip(
            id = "tip_3",
            title = "احذر من خطأ ربط التوازي والتوالي الشائع",
            category = "أخطاء شائعة",
            details = "في ربط التوازي، الخطأ الأكثر شيوعاً هو نسيان قلب الكسر بعد جمع (1/R₁ + 1/R₂)! إذا خرجت لك (1/Req = 3/6)، تذكر أن تقلبها فتصبح Req = 6/3 = 2 Ω، وتأكد دائماً أن المقاومة المكافئة أصغر من أصغر مقاومة."
        ),
        MinisterialTip(
            id = "tip_4",
            title = "لا تترك أي تعليل أو مقارنة فارغة",
            category = "التعاليل والكلاميات",
            details = "التعاليل الوزارية تعتمد على الفهم العلمي للسبب الفيزيائي. اربط دائماً بين الظاهرة وسببها (مثلاً: توصيل السلسلة بالأرض لتفريغ الشحنات، أو صُنع قلب المحولة من صفائح لتقليل التيارات الدوامة)."
        ),
        MinisterialTip(
            id = "tip_5",
            title = "مراجعة المحولة الكهربائية وكلفة الطاقة",
            category = "توقعات وزارية",
            details = "تأكد في مسائل كلفة الطاقة من تحويل الواط إلى كيلوواط (قسمة على 1000) والزمن إلى ساعات، وفي المحولة الكهربائية انتبه لأيهما الملف الابتدائي (1) وأيهما الثانوي (2)."
        ),
        MinisterialTip(
            id = "tip_6",
            title = "الهدوء والثقة بالنفس مفتاح الـ 100",
            category = "تحفيز الأستاذ رافد",
            details = "أنت تعبت وراجعت طوال العام. ابدأ بالفرع الذي تتقنه تماماً ليكسب عقلك دفعة ثقة، ثم عد للأسئلة التي تحتاج تفكيراً أعمق، وراجع أرقامك مرتين قبل تسليم الدفتر."
        )
    )

    val allQuizQuestions: List<QuizQuestion> = listOf(
        // الفصل الأول
        QuizQuestion(
            id = "q_1_1",
            chapterNumber = 1,
            question = "الذرة المتعادلة كهربائياً يكون فيها:",
            options = listOf(
                "عدد الإلكترونات مساوياً لعدد البروتونات",
                "عدد الإلكترونات أكبر من عدد البروتونات",
                "عدد النيوترونات مساوياً لعدد الإلكترونات",
                "لا تحتوي على أي شحنات"
            ),
            correctIndex = 0,
            explanation = "الذرة المتعادلة كهربائياً يتساوى فيها عدد الشحنات السالبة (الإلكترونات) مع عدد الشحنات الموجبة (البروتونات)."
        ),
        QuizQuestion(
            id = "q_1_2",
            chapterNumber = 1,
            question = "عند شحن جسم موصل معزول بطريقة الحث، فإنه يكتسب شحنة مماثلة لشحنة الجسم المؤثر.",
            options = listOf("صح", "خطأ"),
            correctIndex = 1,
            explanation = "خطأ؛ عند الشحن بالحث يكتسب الجسم شحنة مخالفة لشحنة الجسم المؤثر، بينما يكتسب شحنة مماثلة عند الشحن بالتماس.",
            isTrueFalse = true
        ),
        // الفصل الثاني
        QuizQuestion(
            id = "q_2_1",
            chapterNumber = 2,
            question = "أي من المواد الآتية تُعد من المواد الفيرومغناطيسية؟",
            options = listOf("النحاس", "الألمنيوم", "الفولاذ", "البزموث"),
            correctIndex = 2,
            explanation = "الحديد والفولاذ والنيكل والكوبلت مواد فيرومغناطيسية تنجذب بقوة للمغناطيس الاعتيادي."
        ),
        QuizQuestion(
            id = "q_2_2",
            chapterNumber = 2,
            question = "تنتهي خطوط المجال المغناطيسي بتكوين قطب مغناطيسي مفرد عند تقطيع المغناطيس.",
            options = listOf("صح", "خطأ"),
            correctIndex = 1,
            explanation = "خطأ؛ لا يمكن الحصول على قطب مغناطيسي منفرد مهما قُطع المغناطيس، وكل قطعة تمتلك قطباً شمالياً وآخر جنوبياً.",
            isTrueFalse = true
        ),
        // الفصل الثالث
        QuizQuestion(
            id = "q_3_1",
            chapterNumber = 3,
            question = "في ربط المقاومات على التوازي يكون:",
            options = listOf(
                "التيار متساوياً في جميع المقاومات",
                "فرق الجهد متساوياً عبر جميع المقاومات",
                "المقاومة المكافئة أكبر من أكبر مقاومة",
                "عند تلف مقاومة تتوقف الدائرة كلها"
            ),
            correctIndex = 1,
            explanation = "في ربط التوازي يتساوى فرق الجهد عبر طرفي كل مقاومة، بينما يتوزع التيار الكلي."
        ),
        QuizQuestion(
            id = "q_3_2",
            chapterNumber = 3,
            question = "يُربط جهاز الأميتر على التوالي في الدائرة الكهربائية لقياس التيار.",
            options = listOf("صح", "خطأ"),
            correctIndex = 0,
            explanation = "صح؛ مقاومته الداخلية صغيرة جداً لذا يُربط على التوالي ليمر خلاله كل تيار الدائرة.",
            isTrueFalse = true
        ),
        // الفصل الرابع
        QuizQuestion(
            id = "q_4_1",
            chapterNumber = 4,
            question = "وحدة قياس القوة الدافعة الكهربائية (emf) هي الفولت وتعادل:",
            options = listOf("جول / كولوم", "كولوم / ثانية", "واط × ثانية", "أمبير / أوم"),
            correctIndex = 0,
            explanation = "من القانون emf = W / q فإن وحدة الفولت تكافئ جول لكل كولوم (J/C)."
        ),
        // الفصل الخامس
        QuizQuestion(
            id = "q_5_1",
            chapterNumber = 5,
            question = "إحدى الوحدات الآتية لا تُعد وحدة لقياس القدرة الكهربائية:",
            options = listOf("الواط (W)", "جول / ثانية (J/s)", "أمبير × فولت (A·V)", "كيلوواط · ساعة (kWh)"),
            correctIndex = 3,
            explanation = "وحدة كيلوواط · ساعة (kWh) هي وحدة قياس للطاقة الكهربائية المستهلكة وليست للقدرة."
        ),
        // الفصل السادس
        QuizQuestion(
            id = "q_6_1",
            chapterNumber = 6,
            question = "يتوقف انحراف إبرة البوصلة في تجربة أورستد عند انقطاع التيار الكهربائي.",
            options = listOf("صح", "خطأ"),
            correctIndex = 0,
            explanation = "صح؛ لأن المجال المغناطيسي حول السلك ينشأ نتيجة انسياب التيار الكهربائي ويزول بزواله.",
            isTrueFalse = true
        ),
        // الفصل السابع
        QuizQuestion(
            id = "q_7_1",
            chapterNumber = 7,
            question = "إذا كانت نسبة التحويل (N₂ / N₁) في محولة كهربائية أكبر من 1، فإن المحولة:",
            options = listOf("خافضة للفولتية", "رافعة للفولتية", "لا تغير الفولتية", "تعمل بالتيار المستمر"),
            correctIndex = 1,
            explanation = "عندما يكون N₂ > N₁ تكون V₂ > V₁ فتسمى المحولة رافعة للفولتية."
        ),
        // الفصل الثامن
        QuizQuestion(
            id = "q_8_1",
            chapterNumber = 8,
            question = "المادة الأساسية التي تُصنع منها الخلايا الشمسية هي:",
            options = listOf("النحاس", "الحديد المطاوع", "السيليكون شبه الموصل", "الرصاص"),
            correctIndex = 2,
            explanation = "تُصنع الخلايا الشمسية من مواد شبه موصلة مثل السيليكون المشوب."
        ),
        // الفصل التاسع
        QuizQuestion(
            id = "q_9_1",
            chapterNumber = 9,
            question = "الطبقة التي تحدث فيها جميع الظواهر والتقلبات الجوية هي:",
            options = listOf("الستراتوسفير", "التروبوسفير", "الميزوسفير", "الثرموسفير"),
            correctIndex = 1,
            explanation = "التروبوسفير هي الطبقة الأولى الملامسة لسطح الأرض وتحدث فيها الأمطار والغيوم والرياح."
        ),
        QuizQuestion(
            id = "q_9_2",
            chapterNumber = 9,
            question = "تُعكس الموجات الراديوية السماوية بواسطة طبقة الأيونوسفير.",
            options = listOf("صح", "خطأ"),
            correctIndex = 0,
            explanation = "صح؛ طبقة الأيونوسفير عالية التأين فتعكس الموجات السماوية لتمكين الاتصال لمسافات بعيدة.",
            isTrueFalse = true
        )
    )

    fun getChapter(number: Int): PhysicsChapter? = chapters.firstOrNull { it.number == number }

    fun getQuestionsForQuiz(chapterNumber: Int): List<QuizQuestion> {
        return if (chapterNumber == 0) {
            allQuizQuestions
        } else {
            val chapterQuestions = allQuizQuestions.filter { it.chapterNumber == chapterNumber }
            if (chapterQuestions.isNotEmpty()) chapterQuestions else allQuizQuestions.take(5)
        }
    }

    fun searchCurriculum(query: String): List<SearchResultItem> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return emptyList()
        val results = mutableListOf<SearchResultItem>()
        chapters.forEach { ch ->
            ch.definitions.filter {
                it.title.contains(trimmed, ignoreCase = true) ||
                    it.content.contains(trimmed, ignoreCase = true)
            }.forEach { def ->
                results += SearchResultItem(
                    id = def.id,
                    chapterNumber = ch.number,
                    typeLabel = "تعريف • الفصل ${ch.number}",
                    title = def.title,
                    snippet = def.content,
                    favoriteType = FavoriteType.DEFINITION,
                    fullContent = def.content
                )
            }
            ch.reasonings.filter {
                it.question.contains(trimmed, ignoreCase = true) ||
                    it.answer.contains(trimmed, ignoreCase = true)
            }.forEach { res ->
                results += SearchResultItem(
                    id = res.id,
                    chapterNumber = ch.number,
                    typeLabel = "تعليل • الفصل ${ch.number}",
                    title = res.question,
                    snippet = res.answer,
                    favoriteType = FavoriteType.REASONING,
                    fullContent = res.answer
                )
            }
            ch.laws.filter {
                it.name.contains(trimmed, ignoreCase = true) ||
                    it.formula.contains(trimmed, ignoreCase = true) ||
                    it.variables.contains(trimmed, ignoreCase = true)
            }.forEach { law ->
                results += SearchResultItem(
                    id = law.id,
                    chapterNumber = ch.number,
                    typeLabel = "قانون • الفصل ${ch.number}",
                    title = law.name,
                    snippet = law.formula,
                    favoriteType = FavoriteType.LAW,
                    fullContent = "${law.formula}\n${law.variables}"
                )
            }
            ch.solvedProblems.filter {
                it.title.contains(trimmed, ignoreCase = true) ||
                    it.problemText.contains(trimmed, ignoreCase = true)
            }.forEach { prob ->
                results += SearchResultItem(
                    id = prob.id,
                    chapterNumber = ch.number,
                    typeLabel = "مسألة • الفصل ${ch.number}",
                    title = prob.title,
                    snippet = prob.finalAnswer,
                    favoriteType = FavoriteType.PROBLEM,
                    fullContent = "${prob.problemText}\nالحل: ${prob.finalAnswer}"
                )
            }
        }
        return results
    }

    fun solveLaw(type: LawSimulatorType, inputs: List<Double>): SimulationResult {
        fun fmt(value: Double): String =
            if (abs(value) >= 10000 || (abs(value) > 0 && abs(value) < 0.001)) {
                String.format(Locale.US, "%.3e", value)
            } else {
                String.format(Locale.US, "%.3f", value).trimEnd('0').trimEnd('.')
            }

        return when (type) {
            LawSimulatorType.COULOMB -> {
                val q1Micro = inputs[0]
                val q2Micro = inputs[1]
                val rCm = inputs[2].coerceAtLeast(0.0001)
                val q1 = q1Micro * 1e-6
                val q2 = q2Micro * 1e-6
                val r = rCm * 1e-2
                val k = 9e9
                val f = k * abs(q1 * q2) / (r * r)
                SimulationResult(
                    primaryValue = f,
                    formattedAnswer = "${fmt(f)} N",
                    steps = listOf(
                        "تحويل الشحنات: q₁ = ${fmt(q1)} C  |  q₂ = ${fmt(q2)} C",
                        "تحويل المسافة: r = ${fmt(r)} m  ←  r² = ${fmt(r * r)} m²",
                        "F = (9×10⁹ × |q₁ × q₂|) / r² = ${fmt(f)} N"
                    )
                )
            }
            LawSimulatorType.ELECTRIC_FIELD -> {
                val f = inputs[0]
                val qMicro = inputs[1].coerceAtLeast(1e-9)
                val q = qMicro * 1e-6
                val e = f / q
                SimulationResult(
                    primaryValue = e,
                    formattedAnswer = "${fmt(e)} N/C",
                    steps = listOf(
                        "تحويل الشحنة: q' = ${fmt(q)} C",
                        "E = F / q' = ${fmt(f)} / ${fmt(q)} = ${fmt(e)} N/C"
                    )
                )
            }
            LawSimulatorType.CURRENT -> {
                val q = inputs[0]
                val t = inputs[1].coerceAtLeast(0.0001)
                val i = q / t
                SimulationResult(
                    primaryValue = i,
                    formattedAnswer = "${fmt(i)} A",
                    steps = listOf("I = q / t = ${fmt(q)} / ${fmt(t)} = ${fmt(i)} A")
                )
            }
            LawSimulatorType.OHM_LAW -> {
                val v = inputs[0]
                val i = inputs[1].coerceAtLeast(0.0001)
                val r = v / i
                SimulationResult(
                    primaryValue = r,
                    formattedAnswer = "${fmt(r)} Ω",
                    steps = listOf("R = V / I = ${fmt(v)} / ${fmt(i)} = ${fmt(r)} Ω")
                )
            }
            LawSimulatorType.SERIES_RESISTORS -> {
                val r1 = inputs[0]
                val r2 = inputs[1]
                val v = inputs[2]
                val req = r1 + r2
                val i = if (req > 0) v / req else 0.0
                SimulationResult(
                    primaryValue = req,
                    formattedAnswer = "Req = ${fmt(req)} Ω  |  I = ${fmt(i)} A",
                    steps = listOf(
                        "Req = R₁ + R₂ = ${fmt(r1)} + ${fmt(r2)} = ${fmt(req)} Ω",
                        "Iₜ = V / Req = ${fmt(v)} / ${fmt(req)} = ${fmt(i)} A",
                        "V₁ = I × R₁ = ${fmt(i * r1)} V  |  V₂ = I × R₂ = ${fmt(i * r2)} V"
                    )
                )
            }
            LawSimulatorType.PARALLEL_RESISTORS -> {
                val r1 = inputs[0].coerceAtLeast(0.0001)
                val r2 = inputs[1].coerceAtLeast(0.0001)
                val v = inputs[2]
                val req = (r1 * r2) / (r1 + r2)
                val i1 = v / r1
                val i2 = v / r2
                val itotal = i1 + i2
                SimulationResult(
                    primaryValue = req,
                    formattedAnswer = "Req = ${fmt(req)} Ω  |  Iₜ = ${fmt(itotal)} A",
                    steps = listOf(
                        "1/Req = 1/${fmt(r1)} + 1/${fmt(r2)}  ←  Req = ${fmt(req)} Ω",
                        "I₁ = V / R₁ = ${fmt(i1)} A  |  I₂ = V / R₂ = ${fmt(i2)} A",
                        "Iₜ = I₁ + I₂ = ${fmt(itotal)} A"
                    )
                )
            }
            LawSimulatorType.EMF -> {
                val w = inputs[0]
                val q = inputs[1].coerceAtLeast(0.0001)
                val emf = w / q
                SimulationResult(
                    primaryValue = emf,
                    formattedAnswer = "${fmt(emf)} V",
                    steps = listOf("emf = W / q = ${fmt(w)} / ${fmt(q)} = ${fmt(emf)} V")
                )
            }
            LawSimulatorType.ELECTRIC_POWER -> {
                val i = inputs[0]
                val v = inputs[1]
                val p = i * v
                SimulationResult(
                    primaryValue = p,
                    formattedAnswer = "${fmt(p)} W",
                    steps = listOf("P = I × V = ${fmt(i)} × ${fmt(v)} = ${fmt(p)} W")
                )
            }
            LawSimulatorType.ELECTRIC_ENERGY_COST -> {
                val pWatt = inputs[0]
                val timeMin = inputs[1]
                val unitPrice = inputs[2]
                val pKw = pWatt / 1000.0
                val tHours = timeMin / 60.0
                val cost = pKw * tHours * unitPrice
                SimulationResult(
                    primaryValue = cost,
                    formattedAnswer = "${fmt(cost)} دينار",
                    steps = listOf(
                        "P(kW) = ${fmt(pWatt)} / 1000 = ${fmt(pKw)} kW",
                        "t(h) = ${fmt(timeMin)} / 60 = ${fmt(tHours)} h",
                        "Cost = ${fmt(pKw)} × ${fmt(tHours)} × ${fmt(unitPrice)} = ${fmt(cost)} دينار"
                    )
                )
            }
            LawSimulatorType.TRANSFORMER_RATIO -> {
                val v1 = inputs[0].coerceAtLeast(0.0001)
                val n1 = inputs[1].coerceAtLeast(1.0)
                val n2 = inputs[2].coerceAtLeast(1.0)
                val v2 = v1 * (n2 / n1)
                val kind = if (n2 > n1) "محولة رافعة للفولتية" else if (n2 < n1) "محولة خافضة للفولتية" else "محولة عزل"
                SimulationResult(
                    primaryValue = v2,
                    formattedAnswer = "V₂ = ${fmt(v2)} V ($kind)",
                    steps = listOf(
                        "V₂ / V₁ = N₂ / N₁",
                        "V₂ = ${fmt(v1)} × (${fmt(n2)} / ${fmt(n1)}) = ${fmt(v2)} V"
                    )
                )
            }
            LawSimulatorType.TRANSFORMER_EFFICIENCY -> {
                val p2 = inputs[0]
                val p1 = inputs[1].coerceAtLeast(0.0001)
                val eff = (p2 / p1) * 100.0
                val loss = (p1 - p2).coerceAtLeast(0.0)
                SimulationResult(
                    primaryValue = eff,
                    formattedAnswer = "η = ${fmt(eff)}%  |  Ploss = ${fmt(loss)} W",
                    steps = listOf(
                        "η = (P₂ / P₁) × 100% = (${fmt(p2)} / ${fmt(p1)}) × 100% = ${fmt(eff)}%",
                        "القدرة الضائعة: Ploss = P₁ - P₂ = ${fmt(loss)} W"
                    )
                )
            }
            LawSimulatorType.JOULE_HEAT -> {
                val i = inputs[0]
                val r = inputs[1]
                val t = inputs[2]
                val h = i * i * r * t
                SimulationResult(
                    primaryValue = h,
                    formattedAnswer = "${fmt(h)} J",
                    steps = listOf("H = I² × R × t = (${fmt(i)})² × ${fmt(r)} × ${fmt(t)} = ${fmt(h)} J")
                )
            }
        }
    }
}
