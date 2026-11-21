package com.example.data

import com.example.model.DefinitionItem
import com.example.model.LawSimulatorType
import com.example.model.PhysicsChapter
import com.example.model.PhysicsLaw
import com.example.model.ReasoningItem
import com.example.model.SolvedProblem
import com.example.model.SummaryCardItem

internal val chaptersPart1: List<PhysicsChapter> = listOf(
    PhysicsChapter(
        number = 1,
        title = "الكهربائية الساكنة",
        subtitle = "الشحنات، الكشاف الكهربائي، قانون كولوم، والمجال الكهربائي",
        badgeText = "أساس الفيزياء",
        definitions = listOf(
            DefinitionItem(
                id = "ch1_def_1",
                chapterNumber = 1,
                title = "الكهربائية الساكنة",
                content = "هي تجمع الشحنات الكهربائية على سطوح الأجسام المعزولة نتيجة فقدانها أو اكتسابها للإلكترونات.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch1_def_2",
                chapterNumber = 1,
                title = "الكشاف الكهربائي",
                content = "جهاز يستعمل في تجارب الكهربائية الساكنة للكشف عن وجود شحنة كهربائية على جسم ما، ومعرفة نوع تلك الشحنة.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch1_def_3",
                chapterNumber = 1,
                title = "المجال الكهربائي",
                content = "هو الحيز المحيط بالشحنة الكهربائية والذي يظهر فيه تأثير القوة الكهربائية على شحنة اختبارية موجبة موضوعة في أي نقطة فيه.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch1_res_1",
                chapterNumber = 1,
                question = "تجهز سيارات نقل الوقود بسلاسل معدنية في مؤخرتها تلامس الأرض؟",
                answer = "لتفريغ الشحنات الكهربائية الساكنة المتولدة من احتكاك الوقود بجدران الخزان إلى الأرض، منعاً لحدوث شرارة كهربائية تسبب انفجاراً.",
                isMinisterial = true
            ),
            ReasoningItem(
                id = "ch1_res_2",
                chapterNumber = 1,
                question = "عدم انجذاب قصاصات الورق إلى ساق من النحاس مدلوكة بالصوف وممسوكة باليد؟",
                answer = "لأن النحاس مادة موصلة؛ فتتسرب الشحنات الكهربائية المتولدة عليها مباشرة عبر جسم الإنسان إلى الأرض.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch1_sum_1",
                chapterNumber = 1,
                title = "طرق شحن الأجسام بالكهربائية الساكنة",
                categoryLabel = "مقارنة وزارية",
                points = listOf(
                    "الشحن بالدلك: تكتسب المادة شحنة مخالفة لشحنة الجسم الدالك (مثل المطاط والصوف).",
                    "الشحن بالتماس: يكتسب الجسم المعزول شحنة مماثلة لنوع شحنة الجسم الملامس له.",
                    "الشحن بالحث: يُشحن الجسم الموصل المعزول بشحنة مخالفة لشحنة الجسم المؤثر دون تلامس."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch1_law_1",
                chapterNumber = 1,
                name = "قانون كولوم",
                formula = "F = k × (q₁ × q₂) / r²",
                variables = "F: القوة الكهربائية (N) | k = 9×10⁹ N·m²/C² | q₁, q₂: الشحنتان (C) | r: البعد (m)",
                unitNote = "تحويل مهم: 1 µC = 10⁻⁶ C و 1 cm = 10⁻² m",
                simulatorType = LawSimulatorType.COULOMB
            ),
            PhysicsLaw(
                id = "ch1_law_2",
                chapterNumber = 1,
                name = "شدة المجال الكهربائي",
                formula = "E = F / q'",
                variables = "E: المجال الكهربائي (N/C) | F: القوة الكهربائية (N) | q': الشحنة الاختبارية (C)",
                unitNote = "وحدة المجال الكهربائي هي نيوتن / كولوم (N/C)",
                simulatorType = LawSimulatorType.ELECTRIC_FIELD
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch1_prob_1",
                chapterNumber = 1,
                title = "مسألة قانون كولوم (وزاري مكرر)",
                problemText = "شحنتان كهربائيتان نقطيتان موجبتان متماثلتان مقدار كل منهما (3×10⁻⁹ C) والبعد بينهما (5 cm). احسب مقدار قوة التنافر بينهما.",
                givenData = listOf(
                    "q₁ = q₂ = 3 × 10⁻⁹ C",
                    "r = 5 cm = 0.05 m = 5 × 10⁻² m",
                    "k = 9 × 10⁹ N·m²/C²"
                ),
                formulaUsed = "F = k × (q₁ × q₂) / r²",
                steps = listOf(
                    "نحول البعد من السنتيمتر إلى المتر: r = 5 × 10⁻² m، فيكون r² = 25 × 10⁻⁴ m²",
                    "نعوض في قانون كولوم: F = (9×10⁹ × 3×10⁻⁹ × 3×10⁻⁹) / (25×10⁻⁴)",
                    "نحسب البسط: 81 × 10⁻⁹ ÷ (25 × 10⁻⁴) = 3.24 × 10⁻⁵ N"
                ),
                finalAnswer = "F = 3.24 × 10⁻⁵ N (قوة تنافر)",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 2,
        title = "المغناطيسية",
        subtitle = "الأقطاب، المجال المغناطيسي، تصنيف المواد، وطرق التمغنط",
        badgeText = "مفاهيم أساسية",
        definitions = listOf(
            DefinitionItem(
                id = "ch2_def_1",
                chapterNumber = 2,
                title = "الأقطاب المغناطيسية",
                content = "هي مناطق في المغناطيس يكون عندها مقدار القوة المغناطيسية بأعظم ما يمكن، ولا توجد بشكل منفرد بل بشكل أزواج (شمالي وجنوبي).",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch2_def_2",
                chapterNumber = 2,
                title = "المجال المغناطيسي",
                content = "الحيز الذي يحيط بالمغناطيس والذي يظهر فيه تأثير القوى المغناطيسية، ويُمثل بخطوط وهمية مقفلة تتجه من القطب الشمالي إلى الجنوبي خارج المغناطيس.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch2_def_3",
                chapterNumber = 2,
                title = "الحافظة المغناطيسية",
                content = "مادة فيرومغناطيسية تستعمل لحماية الأجهزة كالساعات من التأثيرات المغناطيسية الخارجية، ولحفظ المغانط الدائمة من زوال مغناطيسيتها بمرور الوقت.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch2_res_1",
                chapterNumber = 2,
                question = "يمكن استخدام المغناطيس في رفع قطع الفولاذ السكراب بينما لا يجذب النحاس؟",
                answer = "لأن الفولاذ مادة فيرومغناطيسية ذات قابلية تمغنط عالية، بينما النحاس مادة دايامغناطيسية تتنافر تنافراً ضعيفاً مع المغناطيس القوي.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch2_sum_1",
                chapterNumber = 2,
                title = "تصنيف المواد وفق خواصها المغناطيسية",
                categoryLabel = "تصنيف مهم",
                points = listOf(
                    "الدايامغناطيسية: تتنافر مع المغناطيس القوي تنافراً ضعيفاً (مثل البزموث، النحاس، السيليكون).",
                    "البارامغناطيسية: تنجذب للمغناطيس القوي تجاذباً ضعيفاً (مثل الألمنيوم، الكالسيوم، الأوكسجين السائل).",
                    "الفيرومغناطيسية: تنجذب للمغناطيس الاعتيادي بقوة ولها قابلية تمغنط عالية (مثل الحديد، الفولاذ، النيكل، الكوبلت)."
                )
            ),
            SummaryCardItem(
                id = "ch2_sum_2",
                chapterNumber = 2,
                title = "طرق التمغنط وفقدان المغناطيسية",
                categoryLabel = "خلاصة الفصل",
                points = listOf(
                    "طرق التمغنط: التمغنط بالدلك، والتمغنط بالحث (بالتقريب أو بالتيار الكهربائي المستمر).",
                    "يفقد المغناطيس مغناطيسيته بطريقتين: الطرق القوي، والتسخين الشديد."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch2_law_1",
                chapterNumber = 2,
                name = "قاعدة التجاذب والتنافر المغناطيسي",
                formula = "N ↔ N (تنافر) | N ↔ S (تجاذب)",
                variables = "الأقطاب المتشابهة تتنافر والأقطاب المختلفة تتجاذب ولا يوجد قطب مغناطيسي منفرد.",
                unitNote = "فصل النظري — ركز على المقارنات والتجارب (برادة الحديد والبوصلات).",
                simulatorType = null
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch2_prob_1",
                chapterNumber = 2,
                title = "نشاط الكشف عن خطوط المجال المغناطيسي",
                problemText = "كيف يمكنك عملياً الكشف عن خطوط المجال المغناطيسي لساق مغناطيسية؟",
                givenData = listOf("الأدوات: ساق مغناطيسية، لوح من الزجاج، برادة حديد."),
                formulaUsed = "خطوط المجال المغناطيسي (تُختبر عملياً ببرادة الحديد)",
                steps = listOf(
                    "نضع لوح الزجاج فوق الساق المغناطيسية بمستوى أفقي.",
                    "ننثر برادة الحديد بهدوء فوق لوح الزجاج وننقر اللوح برفق.",
                    "نلاحظ أن برادة الحديد تترتب بشكل خطوط منحنية مقفلة تتركز عند القطبين، وهي تمثل خطوط المجال المغناطيسي."
                ),
                finalAnswer = "تترتب البرادة على هيئة خطوط مقفلة من القطب الشمالي إلى الجنوبي.",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 3,
        title = "التيار الكهربائي",
        subtitle = "قانون أوم، المقاومة، وربط المقاومات على التوالي والتوازي",
        badgeText = "الأكثر أهمية وزارياً",
        definitions = listOf(
            DefinitionItem(
                id = "ch3_def_1",
                chapterNumber = 3,
                title = "المقاومة الكهربائية (الأوم)",
                content = "الإعاقة التي يبديها المقاوم للتيار الكهربائي المار خلاله، ووحدة قياسها الأوم (Ω) وهي مقاومة موصل يمر فيه تيار 1A عندما يكون فرق الجهد 1V.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch3_def_2",
                chapterNumber = 3,
                title = "التيار الكهربائي",
                content = "مقدار الشحنات الكهربائية الكلية التي تعبر مقطعاً عرضياً في موصل خلال وحدة الزمن، ويقاس بوحدة الأمبير (A).",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch3_res_1",
                chapterNumber = 3,
                question = "تُربط الأجهزة الكهربائية والمصابيح في المنازل على التوازي؟",
                answer = "لكي يعمل كل جهاز بفرق جهد متساوٍ (220V)، وعند تلف أو إطفاء أحد الأجهزة لا تنقطع الدائرة عن بقية الأجهزة، كما تقل المقاومة المكافئة.",
                isMinisterial = true
            ),
            ReasoningItem(
                id = "ch3_res_2",
                chapterNumber = 3,
                question = "يُربط الأميتر على التوالي والفولطميتر على التوازي مع الحمل؟",
                answer = "الأميتر مقاومته صغيرة جداً فيُربط على التوالي ليمر فيه كل التيار دون تغييره، أما الفولطميتر فمقاومته كبيرة جداً فيُربط على التوازي لقياس فرق الجهد دون سحب تيار مؤثر.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch3_sum_1",
                chapterNumber = 3,
                title = "العوامل المؤثرة على مقاومة الموصل",
                categoryLabel = "سؤال وزاري ثابت",
                points = listOf(
                    "درجة الحرارة: تزداد مقاومة الموصلات النقية بزيادة درجة الحرارة.",
                    "طول الموصل (L): تتناسب المقاومة طردياً مع طول الموصل.",
                    "مساحة المقطع العرضي (A): تتناسب المقاومة عكسياً مع مساحة المقطع.",
                    "نوع المادة: تختلف المقاومة النوعية باختلاف المادة (النحاس أقل من الحديد)."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch3_law_1",
                chapterNumber = 3,
                name = "قانون التيار الكهربائي",
                formula = "I = q / t",
                variables = "I: التيار (A) | q: الشحنة (C) | t: الزمن (s)",
                unitNote = "إذا أُعطي الزمن بالدقائق يُضرب في 60 للتحويل إلى ثوانٍ.",
                simulatorType = LawSimulatorType.CURRENT
            ),
            PhysicsLaw(
                id = "ch3_law_2",
                chapterNumber = 3,
                name = "قانون أوم",
                formula = "R = V / I",
                variables = "R: المقاومة (Ω) | V: فرق الجهد (V) | I: التيار (A)",
                unitNote = "V = I × R  |  I = V / R",
                simulatorType = LawSimulatorType.OHM_LAW
            ),
            PhysicsLaw(
                id = "ch3_law_3",
                chapterNumber = 3,
                name = "ربط المقاومات على التوالي",
                formula = "Req = R₁ + R₂ | Iₜ = I₁ = I₂ | Vₜ = V₁ + V₂",
                variables = "التيار متساوٍ في جميع المقاومات، والمقاومة المكافئة أكبر من أي مقاومة.",
                unitNote = "مهم جداً: لا تنسَ حساب V₁ = I × R₁ و V₂ = I × R₂.",
                simulatorType = LawSimulatorType.SERIES_RESISTORS
            ),
            PhysicsLaw(
                id = "ch3_law_4",
                chapterNumber = 3,
                name = "ربط المقاومات على التوازي",
                formula = "1/Req = 1/R₁ + 1/R₂ | Vₜ = V₁ = V₂ | Iₜ = I₁ + I₂",
                variables = "فرق الجهد متساوٍ، والمقاومة المكافئة أصغر من أصغر مقاومة.",
                unitNote = "تذكّر قلب الكسر بعد الجمع لإيجاد قيمة Req!",
                simulatorType = LawSimulatorType.PARALLEL_RESISTORS
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch3_prob_1",
                chapterNumber = 3,
                title = "مسألة ربط التوازي (وزاري مهم)",
                problemText = "مقاومتان (R₁ = 6 Ω و R₂ = 3 Ω) ربطتا على التوازي، ثم وصلتا إلى مصدر فرق جهده (12 V). احسب: 1) المقاومة المكافئة، 2) التيار المار في كل مقاومة، 3) التيار الكلي.",
                givenData = listOf("R₁ = 6 Ω", "R₂ = 3 Ω", "Vₜ = 12 V (ربط توازي)"),
                formulaUsed = "1/Req = 1/R₁ + 1/R₂  |  I = V / R",
                steps = listOf(
                    "1/Req = 1/6 + 1/3 = (1 + 2)/6 = 3/6 = 1/2  ←  إذاً Req = 2 Ω",
                    "بما أن الربط توازي فإن Vₜ = V₁ = V₂ = 12 V",
                    "I₁ = V₁ / R₁ = 12 / 6 = 2 A  |  I₂ = V₂ / R₂ = 12 / 3 = 4 A",
                    "Iₜ = I₁ + I₂ = 2 + 4 = 6 A (وللتحقق: Vₜ / Req = 12 / 2 = 6 A)"
                ),
                finalAnswer = "Req = 2 Ω  |  I₁ = 2 A, I₂ = 4 A  |  Iₜ = 6 A",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 4,
        title = "البطارية والقوة الدافعة الكهربائية",
        subtitle = "أنواع البطاريات، القوة الدافعة الكهربائية (emf)، والمقاومة الداخلية",
        badgeText = "فصل مختصر ومضمون",
        definitions = listOf(
            DefinitionItem(
                id = "ch4_def_1",
                chapterNumber = 4,
                title = "القوة الدافعة الكهربائية (emf)",
                content = "مقدار الطاقة التي تزودها البطارية لوحدة الشحنة الكهربائية (1 C) لتحريكها في الدائرة، وتقاس بوحدة الفولت (V) أي (J/C).",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch4_def_2",
                chapterNumber = 4,
                title = "البطارية الثانوية",
                content = "بطارية يمكن إعادة شحنها لأن التفاعلات الكيميائية بداخلها تفاعلات انعكاسية، مثل بطارية السيارة وبطارية أيون الليثيوم.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch4_res_1",
                chapterNumber = 4,
                question = "تُربط خلايا بطارية السيارة على التوالي بينما تربط الألواح أو الخلايا أحياناً على التوازي؟",
                answer = "الربط على التوالي يزيد الفولتية الكلية (6 خلايا × 2V = 12V)، بينما الربط على التوازي يزيد من سعة التيار المجهز.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch4_sum_1",
                chapterNumber = 4,
                title = "مكونات بطارية أيون الليثيوم ومزاياها",
                categoryLabel = "نقاط مركزة",
                points = listOf(
                    "تمتاز بكثافة طاقة عالية وخفة وزن وطول عمر وعدم تسرب السائل.",
                    "تُستعمل في الهواتف الذكية والحواسيب المحمولة والكاميرات.",
                    "الخلية الجافة (البطارية الأولية) لا يمكن إعادة شحنها كخلية الكاربون - خارصين."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch4_law_1",
                chapterNumber = 4,
                name = "قانون القوة الدافعة الكهربائية",
                formula = "emf = W / q",
                variables = "emf: القوة الدافعة (V) | W: الشغل أو الطاقة المكتسبة (J) | q: الشحنة (C)",
                unitNote = "1 Volt = 1 Joule / 1 Coulomb",
                simulatorType = LawSimulatorType.EMF
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch4_prob_1",
                chapterNumber = 4,
                title = "مسألة القوة الدافعة الكهربائية",
                problemText = "انسابت كمية من الشحنات الكهربائية مقدارها (10 C) خلال بطارية فاكتسبت طاقة مقدارها (20 J). احسب مقدار القوة الدافعة الكهربائية (emf) للبطارية.",
                givenData = listOf("q = 10 C", "W = 20 J"),
                formulaUsed = "emf = W / q",
                steps = listOf(
                    "نكتب القانون: emf = W / q",
                    "نعوض المعطيات: emf = 20 / 10 = 2 V"
                ),
                finalAnswer = "emf = 2 V",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 5,
        title = "الطاقة والقدرة الكهربائية",
        subtitle = "القدرة، كلفة الطاقة الكهربائية، الفاصم، والسلك المؤرض",
        badgeText = "مسائل حياتية مهمة",
        definitions = listOf(
            DefinitionItem(
                id = "ch5_def_1",
                chapterNumber = 5,
                title = "القدرة الكهربائية",
                content = "مقدار الطاقة التي يستهلكها الجهاز الكهربائي في وحدة الزمن، وتقاس بوحدة الواط (Watt) والتي تعادل جول/ثانية.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch5_def_2",
                chapterNumber = 5,
                title = "الفاصم الكهربائي (الفيوز)",
                content = "سلك فلزي لا يتحمل تياراً يزيد عن حد معين؛ فإذا تجاوز التيار ذلك الحد يسخن وينصهر ليقطع الدائرة ويحمي الأجهزة من التلف.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch5_res_1",
                chapterNumber = 5,
                question = "يربط قاطع الدورة (الفاصم) على التوالي مع السلك الحي (الحار) قبل دخول التيار للجهاز؟",
                answer = "لكي يقوم بقطع الدائرة الكهربائية فوراً عند مرور تيار أكبر من المسموح به قبل وصوله إلى الجهاز الكهربائي.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch5_sum_1",
                chapterNumber = 5,
                title = "إجراءات السلامة في الدوائر المنزلية",
                categoryLabel = "ثوابت وزارية",
                points = listOf(
                    "السلك الحي (L): جهده 220V، السلك المتعادل (N): جهده يقارب الصفر.",
                    "السلك المؤرض (E): يربط الغلاف المعدني للأجهزة بالأرض لحماية الإنسان من الصعقة.",
                    "تجنب لمس الأجهزة بأيدٍ مبللة لأن الماء يقلل مقاومة الجلد."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch5_law_1",
                chapterNumber = 5,
                name = "قانون القدرة الكهربائية",
                formula = "P = I × V  |  P = I² × R  |  P = V² / R",
                variables = "P: القدرة (W) | I: التيار (A) | V: الجهد (V) | R: المقاومة (Ω)",
                unitNote = "1 kW = 1000 W",
                simulatorType = LawSimulatorType.ELECTRIC_POWER
            ),
            PhysicsLaw(
                id = "ch5_law_2",
                chapterNumber = 5,
                name = "الطاقة الكهربائية والكلفة",
                formula = "E = P × t  |  Cost = P(kW) × t(h) × Unit Price",
                variables = "E: الطاقة (J أو kWh) | P: القدرة | t: الزمن | Unit Price: ثمن الوحدة",
                unitNote = "في مسائل الكلفة حوّل القدرة إلى kW (÷1000) والزمن إلى ساعات (h)!",
                simulatorType = LawSimulatorType.ELECTRIC_ENERGY_COST
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch5_prob_1",
                chapterNumber = 5,
                title = "مسألة حساب كلفة الطاقة الكهربائية (وزاري)",
                problemText = "استعملت مكنسة كهربائية لمدة (30 دقيقة) وكانت قدرتها (1000 W)، إذا كان ثمن الوحدة الواحدة (100 دينار / kWh). احسب المبلغ الواجب دفعه.",
                givenData = listOf(
                    "P = 1000 W = 1000 / 1000 = 1 kW",
                    "t = 30 min = 30 / 60 = 0.5 h",
                    "ثمن الوحدة = 100 دينار"
                ),
                formulaUsed = "Cost = P(kW) × t(h) × ثمن الوحدة",
                steps = listOf(
                    "نحول القدرة إلى كيلوواط: P = 1000 / 1000 = 1 kW",
                    "نحول الزمن إلى ساعات: t = 30 / 60 = 0.5 h",
                    "نحسب الكلفة: Cost = 1 × 0.5 × 100 = 50 دينار"
                ),
                finalAnswer = "الكلفة = 50 ديناراً",
                isMinisterial = true
            )
        )
    )
)
