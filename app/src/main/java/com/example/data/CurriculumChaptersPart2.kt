package com.example.data

import com.example.model.DefinitionItem
import com.example.model.LawSimulatorType
import com.example.model.PhysicsChapter
import com.example.model.PhysicsLaw
import com.example.model.ReasoningItem
import com.example.model.SolvedProblem
import com.example.model.SummaryCardItem

internal val chaptersPart2: List<PhysicsChapter> = listOf(
    PhysicsChapter(
        number = 6,
        title = "الكهربائية والمغناطيسية",
        subtitle = "تجربة أورستد، المغناطيس الكهربائي، الحث الكهرومغناطيسي، والمحرك",
        badgeText = "الكهرومغناطيسية",
        definitions = listOf(
            DefinitionItem(
                id = "ch6_def_1",
                chapterNumber = 6,
                title = "الحث الكهرومغناطيسي",
                content = "ظاهرة توليد قوة دافعة كهربائية محتثة (وتيار محتث في دائرة مقفلة) نتيجة تغير المجال المغناطيسي الذي يخترق الموصل خلال الزمن.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch6_def_2",
                chapterNumber = 6,
                title = "المغناطيس الكهربائي",
                content = "مغناطيس مؤقت يزول بزوال التيار الكهربائي المنساب في السلك، ويتكون من قلب حديد مطاوع ملفوف حوله سلك معزول.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch6_res_1",
                chapterNumber = 6,
                question = "يفضل استعمال الحديد المطاوع قلباً للمغناطيس الكهربائي بدلاً من الفولاذ؟",
                answer = "لأن الحديد المطاوع يكتسب المغناطيسية بسرعة ويفقدها مباشرة عند انقطاع التيار (مغناطيس مؤقت)، بينما الفولاذ يحتفظ بالمغناطيسية.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch6_sum_1",
                chapterNumber = 6,
                title = "مقارنة المولد الكهربائي والمحرك الكهربائي",
                categoryLabel = "مقارنة وزارية",
                points = listOf(
                    "المولد الكهربائي: يحول الطاقة الميكانيكية (الحركية) إلى طاقة كهربائية بوجود مجال مغناطيسي.",
                    "المحرك الكهربائي: يحول الطاقة الكهربائية إلى طاقة ميكانيكية بوجود مجال مغناطيسي.",
                    "قاعدة الكف اليمنى: الإبهام يشير للتيار ولفة الأصابع تشير لاتجاه المجال المغناطيسي."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch6_law_1",
                chapterNumber = 6,
                name = "قانون جول للتأثير الحراري للتيار",
                formula = "H = I² × R × t",
                variables = "H: الحرارة المتولدة (J) | I: التيار (A) | R: المقاومة (Ω) | t: الزمن (s)",
                unitNote = "تتناسب الطاقة الحرارية طردياً مع مربع التيار والمقاومة والزمن.",
                simulatorType = LawSimulatorType.JOULE_HEAT
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch6_prob_1",
                chapterNumber = 6,
                title = "كيف تزداد شدة المغناطيس الكهربائي؟",
                problemText = "اذكر العوامل التي يعتمد عليها مقدار المجال المغناطيسي للمغناطيس الكهربائي.",
                givenData = listOf("مغناطيس كهربائي ذو قلب حديد مطاوع"),
                formulaUsed = "علاقة طردية مع عدد اللفات والتيار ونوع القلب",
                steps = listOf(
                    "1. زيادة عدد لفات الملف لوحدة الطول.",
                    "2. زيادة مقدار التيار الكهربائي المستمر المنساب في الملف.",
                    "3. نوع مادة القلب (استعمال الحديد المطاوع بدلاً من الهواء)."
                ),
                finalAnswer = "يزداد المجال بزيادة اللفات والتيار واستعمال قلب الحديد المطاوع.",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 7,
        title = "المحولة الكهربائية",
        subtitle = "المحولة الرافعة والخافضة، نسبة التحويل، وكفاءة المحولة",
        badgeText = "فصل مسائل مضمونة",
        definitions = listOf(
            DefinitionItem(
                id = "ch7_def_1",
                chapterNumber = 7,
                title = "المحولة الكهربائية",
                content = "جهاز يعمل على رفع أو خفض الفولتية المتناوبة (AC) فيؤدي بالتالي إلى خفض أو رفع التيار اعتماداً على مبدأ الحث المتبادل بين ملفين.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch7_def_2",
                chapterNumber = 7,
                title = "التيارات الدوامة",
                content = "تيارات محتثة تتولد في قلب الحديد للمحولة نتيجة تغير خطوط المجال المغناطيسي وتسبب فقداناً للقدرة بشكل حرارة، وتقلل بصنع القلب بشكل صفائح معزولة.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch7_res_1",
                chapterNumber = 7,
                question = "لا تعمل المحولة الكهربائية على التيار المستمر (البطارية)؟",
                answer = "لأن التيار المستمر ثابت الشدة والاتجاه، فلا يولد تغيراً في الفيض المغناطيسي في الملف الابتدائي، وبالتالي لا يتولد تيار محتث في الثانوي.",
                isMinisterial = true
            ),
            ReasoningItem(
                id = "ch7_res_2",
                chapterNumber = 7,
                question = "ترفع الفولتية إلى قيم عالية جداً عند نقل الطاقة الكهربائية لمسافات بعيدة؟",
                answer = "لتقليل مقدار التيار المار في الأسلاك، مما يقلل من خسائر الطاقة الحرارية (I²R) في خطوط النقل.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch7_sum_1",
                chapterNumber = 7,
                title = "المحولة الخافضة مقابل المحولة الرافعة",
                categoryLabel = "مقارنة ذهبية",
                points = listOf(
                    "المحولة الخافضة: N₂ < N₁ و V₂ < V₁ و I₂ > I₁ (مثل شاحنة الموبايل).",
                    "المحولة الرافعة: N₂ > N₁ و V₂ > V₁ و I₂ < I₁ (مثل محطات التوليد وأجهزة التلفاز).",
                    "نسبة التحويل (N₂ / N₁): أقل من 1 في الخافضة وأكبر من 1 في الرافعة."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch7_law_1",
                chapterNumber = 7,
                name = "نسبة التحويل في المحولة المثالية",
                formula = "V₂ / V₁ = N₂ / N₁  |  I₁ / I₂ = N₂ / N₁",
                variables = "V₁, V₂: فولتية الابتدائي والثانوي | N₁, N₂: عدد لفات الابتدائي والثانوي",
                unitNote = "التيار يتناسب عكسياً مع الفولتية وعدد اللفات في المحولة المثالية.",
                simulatorType = LawSimulatorType.TRANSFORMER_RATIO
            ),
            PhysicsLaw(
                id = "ch7_law_2",
                chapterNumber = 7,
                name = "كفاءة المحولة الكهربائية",
                formula = "η = (P₂ / P₁) × 100%",
                variables = "η: الكفاءة (%) | P₂: القدرة الخارجة (W) | P₁: القدرة الداخلة (W)",
                unitNote = "خسارة القدرة: Ploss = P₁ - P₂",
                simulatorType = LawSimulatorType.TRANSFORMER_EFFICIENCY
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch7_prob_1",
                chapterNumber = 7,
                title = "مسألة المحولة الكهربائية (وزاري)",
                problemText = "محولة كهربائية رُبط ملفها الابتدائي مع مصدر فولتيته (240 V) وعدد لفاته (500 لفة)، والجهاز المربوط مع الملف الثانوي يعمل على فولتية (12 V). احسب عدد لفات الملف الثانوي ونوع المحولة.",
                givenData = listOf("V₁ = 240 V", "N₁ = 500 turns", "V₂ = 12 V"),
                formulaUsed = "V₂ / V₁ = N₂ / N₁",
                steps = listOf(
                    "نعوض في قانون نسبة التحويل: 12 / 240 = N₂ / 500",
                    "N₂ = (12 × 500) / 240 = 6000 / 240 = 25 لفة",
                    "بما أن V₂ < V₁ و N₂ < N₁ فهي محولة خافضة."
                ),
                finalAnswer = "N₂ = 25 لفة (محولة خافضة للفولتية)",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 8,
        title = "تكنولوجيا مصادر الطاقة",
        subtitle = "المصادر الأحفورية، الطاقة الشمسية، طاقة الرياح، والطاقة المتجددة",
        badgeText = "فصل نظري سهل",
        definitions = listOf(
            DefinitionItem(
                id = "ch8_def_1",
                chapterNumber = 8,
                title = "الخلية الشمسية (الفوتوفولطية)",
                content = "أداة مصنوعة من مواد شبه موصلة كالسيليكون تحول طاقة ضوء الشمس مباشرة إلى طاقة كهربائية (تيار مستمر).",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch8_def_2",
                chapterNumber = 8,
                title = "الوقود الحيوي",
                content = "طاقة مستثمرة من الكائنات الحية النباتية والحيوانية مثل وقود الإيثانول السائل ووقود الديزل الحيوي.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch8_res_1",
                chapterNumber = 8,
                question = "تُفضل مصادر الطاقة المتجددة على مصادر الطاقة الأحفورية؟",
                answer = "لأنها طاقة لا تنفد، نظيفة غير ملوثة للبيئة، متاحة محلياً، وقليلة التكاليف التشغيلية مقارنة بالوقود الأحفوري.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch8_sum_1",
                chapterNumber = 8,
                title = "أنواع مصادر الطاقة المتجددة",
                categoryLabel = "خلاصة وزارية",
                points = listOf(
                    "الطاقة الشمسية: تشغيل الخلايا الشمسية والسخانات الشمسية وتحلية المياه.",
                    "طاقة الرياح: تدوير توربينات هوائية لتوليد الكهرباء (تعتمد على سرعة الرياح).",
                    "الطاقة المائية وطاقة المد والجزر: استثمار طاقة المياه المتدفقة وحركة المد."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch8_law_1",
                chapterNumber = 8,
                name = "مقارنة مصادر الطاقة",
                formula = "أحفورية (نفط، فحم، غاز) ⛔ تنفد | متجددة (شمس، رياح، ماء) ♻️ مستدامة",
                variables = "في المسائل الوزارية يُطلب عادة تعداد مزايا الطاقة المتجددة أو طرق تحويل الطاقة.",
                unitNote = "ركز على مبدأ عمل الخلية الشمسية واستعمالات الطاقة النووية السلمية.",
                simulatorType = null
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch8_prob_1",
                chapterNumber = 8,
                title = "سؤال وزاري: مميزات الطاقة الشمسية",
                problemText = "ما هي المميزات الرئيسية لاستثمار الطاقة الشمسية؟",
                givenData = listOf("مقارنة مع مصادر الوقود الأحفوري"),
                formulaUsed = "تعداد نقاط وزاري",
                steps = listOf(
                    "1. سهولة توفرها في معظم بقاع العالم وخاصة العراق.",
                    "2. خلوها من أي تأثيرات سلبية على البيئة ولا تسبب تلوثاً.",
                    "3. لا تطلق غازات سامة أو ملوثة للجو."
                ),
                finalAnswer = "متوفرة، نظيفة، وصديقة للبيئة.",
                isMinisterial = true
            )
        )
    ),
    PhysicsChapter(
        number = 9,
        title = "فيزياء الجو وتقنية الاتصالات الحديثة",
        subtitle = "طبقات الغلاف الجوي، الموجات الراديوية، والأقمار الصناعية",
        badgeText = "ختام المنهج",
        definitions = listOf(
            DefinitionItem(
                id = "ch9_def_1",
                chapterNumber = 9,
                title = "طبقة الأوزون (O₃)",
                content = "طبقة تقع في الستراتوسفير تعمل كمظلة واقية تحجب الأشعة فوق البنفسجية الضارة (UV-C) عن الكائنات الحية على سطح الأرض.",
                isMinisterial = true
            ),
            DefinitionItem(
                id = "ch9_def_2",
                chapterNumber = 9,
                title = "القمر الصناعي",
                content = "تابع يدور حول الأرض يحمل أجهزة ومعدات إلكترونية تُستعمل في الاتصالات والأرصاد الجوية والأغراض العلمية والعسكرية.",
                isMinisterial = true
            )
        ),
        reasonings = listOf(
            ReasoningItem(
                id = "ch9_res_1",
                chapterNumber = 9,
                question = "تُعكس الموجات الراديوية السماوية من طبقة الأيونوسفير لتغطي مسافات بعيدة؟",
                answer = "لأن طبقة الأيونوسفير تكون عالية التأين فتعمل كمرآة عاكسة للموجات الراديوية ذات الترددات المحددة نحو الأرض.",
                isMinisterial = true
            )
        ),
        summaries = listOf(
            SummaryCardItem(
                id = "ch9_sum_1",
                chapterNumber = 9,
                title = "طبقات الغلاف الجوي الخمس بالترتيب",
                categoryLabel = "حفظ مهم جداً",
                points = listOf(
                    "1. التروبوسفير: أقرب طبقة للأرض وتحدث فيها التقلبات الجوية والأمطار.",
                    "2. الستراتوسفير: تحتوي على طبقة الأوزون وتزداد حرارتها مع الارتفاع.",
                    "3. الميزوسفير: أبرد طبقات الجو وتحترق فيها الشهب والنيازك.",
                    "4. الثرموسفير (الأيونوسفير): ساخنة جداً وتنعكس عنها الموجات الراديوية.",
                    "5. الإكسوسفير: أعلى طبقة وتتحرك فيها الغازات نحو الفضاء الخارجي."
                )
            )
        ),
        laws = listOf(
            PhysicsLaw(
                id = "ch9_law_1",
                chapterNumber = 9,
                name = "مكونات منظومة الاتصالات",
                formula = "وحدة الإرسال ← قناة الاتصال (سلكية أو لاسلكية) ← وحدة الاستقبال",
                variables = "الموجات الأرضية: قريبة المدى | الموجات السماوية: بعيدة المدى عبر الأيونوسفير.",
                unitNote = "الليف البصري ينقل الإشارات الضوئية بسرعات عالية ودون تداخل كهرومغناطيسي.",
                simulatorType = null
            )
        ),
        solvedProblems = listOf(
            SolvedProblem(
                id = "ch9_prob_1",
                chapterNumber = 9,
                title = "التمييز بين الموجات الأرضية والسماوية",
                problemText = "قارن بإيجاز بين انتشار الموجات الأرضية وانتشار الموجات السماوية.",
                givenData = listOf("فصل فيزياء الجو والاتصالات"),
                formulaUsed = "مقارنة علمية",
                steps = listOf(
                    "الموجات الأرضية: تنتقل قريبة من سطح الأرض وتتبع انحناءها، مداها قصير نسبياً (تردد أقل من 200 MHz).",
                    "الموجات السماوية: تُستعمل في الاتصالات بعيدة المدى حيث تنعكس عن طبقة الأيونوسفير لتصل إلى مسافات بعيدة."
                ),
                finalAnswer = "الأرضية قريبة المدى وموازية للسطح؛ السماوية تنعكس عبر الأيونوسفير.",
                isMinisterial = true
            )
        )
    )
)
