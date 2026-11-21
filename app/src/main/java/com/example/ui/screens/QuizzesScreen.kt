package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.CurriculumRepository
import com.example.model.QuizResult
import com.example.model.UiState
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionBadge
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseContainer
import com.example.ui.theme.RoseError
import com.example.viewmodel.ActiveQuizUiState
import com.example.viewmodel.QuizViewModel
import java.util.Locale

@Composable
fun QuizzesScreen(
    quizViewModel: QuizViewModel,
    quizResultsState: UiState<List<QuizResult>>,
    onSaveQuizResult: (quizTitle: String, chapterNumber: Int, score: Int, total: Int, durationSec: Int) -> Unit
) {
    val activeState by quizViewModel.state.collectAsStateWithLifecycle()

    if (activeState.isActive) {
        BackHandler { quizViewModel.exitQuiz() }
        if (activeState.isFinished) {
            QuizReviewContent(
                state = activeState,
                onRestart = { quizViewModel.startQuiz(activeState.chapterNumber, activeState.quizTitle) },
                onExit = { quizViewModel.exitQuiz() }
            )
        } else {
            QuizRunnerContent(
                state = activeState,
                onSelectAnswer = quizViewModel::selectAnswer,
                onNext = quizViewModel::nextQuestion,
                onPrevious = quizViewModel::previousQuestion,
                onSubmit = {
                    quizViewModel.finishQuiz { title, ch, score, total, dur ->
                        onSaveQuizResult(title, ch, score, total, dur)
                    }
                },
                onExit = { quizViewModel.exitQuiz() }
            )
        }
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "الاختبارات الذكية (امتحن نفسك)",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "اختبارات إلكترونية بأسئلة اختيارات من متعدد وصح/خطأ مع تصحيح فوري وتوضيح السبب.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Comprehensive Ministerial Quiz Featured Card
            item {
                GlassCard(
                    isGoldAccent = true,
                    onClick = {
                        quizViewModel.startQuiz(
                            chapterNumber = 0,
                            quizTitle = "الاختبار الوزاري الشامل (الفصول 1 - 9)"
                        )
                    },
                    modifier = Modifier.testTag("start_ministerial_quiz")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SectionBadge(text = "شامل المنهج", isGold = true)
                        Icon(
                            imageVector = Icons.Filled.EmojiEvents,
                            contentDescription = null,
                            tint = CalmGold,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "الاختبار الوزاري الشامل",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${CurriculumRepository.allQuizQuestions.size} سؤالاً شاملاً لجميع فصول الكتاب مع عداد وقت وتصحيح فوري.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            quizViewModel.startQuiz(
                                chapterNumber = 0,
                                quizTitle = "الاختبار الوزاري الشامل (الفصول 1 - 9)"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CalmGold,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text("ابدأ الاختبار الشامل الآن", fontWeight = FontWeight.Bold)
                    }
                }
            }

            item {
                Text(
                    text = "اختبارات الفصول",
                    style = MaterialTheme.typography.titleMedium,
                    color = ElectricBlue
                )
            }

            items(CurriculumRepository.chapters, key = { it.number }) { ch ->
                val count = CurriculumRepository.getQuestionsForQuiz(ch.number).size
                GlassCard(
                    onClick = {
                        quizViewModel.startQuiz(
                            chapterNumber = ch.number,
                            quizTitle = "اختبار الفصل ${ch.number}: ${ch.title}"
                        )
                    },
                    modifier = Modifier.testTag("start_chapter_quiz_${ch.number}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "الفصل ${ch.number}: ${ch.title}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "$count أسئلة تفاعلية (اختيارات + صح/خطأ)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "ابدأ الاختبار",
                            tint = ElectricBlue
                        )
                    }
                }
            }

            item {
                Text(
                    text = "سجل درجاتك السابقة (محفوظ سحابياً)",
                    style = MaterialTheme.typography.titleMedium,
                    color = CalmGold
                )
            }

            when (quizResultsState) {
                is UiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = ElectricBlue)
                        }
                    }
                }
                is UiState.Error -> {
                    item {
                        GlassCard {
                            Text(
                                text = quizResultsState.message,
                                color = RoseError,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                is UiState.Success -> {
                    if (quizResultsState.data.isEmpty()) {
                        item {
                            GlassCard {
                                Text(
                                    text = "لم تكمل أي اختبار بعد. جرّب اختبار الفصل الأول الآن لتسجيل نتيجتك الأولى!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        items(quizResultsState.data.take(10), key = { it.id }) { res ->
                            GlassCard {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = res.quizTitle,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "الزمن: ${formatDuration(res.durationSeconds.toInt())}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    SectionBadge(
                                        text = "${res.score} / ${res.totalQuestions}",
                                        isGold = res.score * 2 >= res.totalQuestions
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun QuizRunnerContent(
    state: ActiveQuizUiState,
    onSelectAnswer: (String, Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSubmit: () -> Unit,
    onExit: () -> Unit
) {
    val question = state.currentQuestion ?: return
    val selected = state.selectedAnswers[question.id]
    val progress = (state.currentIndex + 1).toFloat() / state.questions.size.coerceAtLeast(1)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onExit) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "خروج من الاختبار",
                        tint = ElectricBlue
                    )
                }
                Text(
                    text = state.quizTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = ElectricBlueContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "الوقت",
                            tint = ElectricBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = formatDuration(state.elapsedSeconds),
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricBlue
                        )
                    }
                }
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = ElectricBlue,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Text(
                text = "السؤال ${state.currentIndex + 1} من ${state.questions.size}",
                style = MaterialTheme.typography.labelLarge,
                color = CalmGold
            )

            GlassCard {
                SectionBadge(
                    text = if (question.isTrueFalse) "صح أم خطأ" else "اختر الإجابة الصحيحة",
                    isGold = question.isTrueFalse
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                question.options.forEachIndexed { idx, option ->
                    val isSelected = selected == idx
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectAnswer(question.id, idx) }
                            .testTag("quiz_option_$idx"),
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) ElectricBlueContainer else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(
                            width = if (isSelected) 1.6.dp else 1.dp,
                            color = if (isSelected) ElectricBlue else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isSelected) ElectricBlue else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "مختار",
                                    tint = ElectricBlue
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (state.currentIndex > 0) {
                    OutlinedButton(
                        onClick = onPrevious,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("السابق")
                    }
                }
                if (state.currentIndex < state.questions.lastIndex) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("quiz_next_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("التالي", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onSubmit,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("quiz_submit_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CalmGold,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("إنهاء وعرض النتيجة", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizReviewContent(
    state: ActiveQuizUiState,
    onRestart: () -> Unit,
    onExit: () -> Unit
) {
    val total = state.questions.size.coerceAtLeast(1)
    val score = state.score
    val percent = (score * 100) / total

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                GlassCard(isGoldAccent = true) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SectionBadge(text = "النتيجة النهائية", isGold = true)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$score / $total  ($percent%)",
                            style = MaterialTheme.typography.displaySmall,
                            color = if (percent >= 70) EmeraldSuccess else CalmGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = when {
                                percent >= 90 -> "أداء بطولي! أنت على طريق الـ 100 في الوزاري"
                                percent >= 70 -> "جيد جداً! راجع الأسئلة الخاطئة أدناه لتصل للكمال"
                                else -> "بداية طيبة! راجع توضيح الأسباب وأعد الاختبار"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "الوقت المستغرق: ${formatDuration(state.elapsedSeconds)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = ElectricBlue
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(onClick = onRestart) {
                                Text("إعادة الاختبار")
                            }
                            OutlinedButton(onClick = onExit) {
                                Text("العودة للاختبارات")
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "تصحيح الإجابات وتوضيح السبب",
                    style = MaterialTheme.typography.titleMedium,
                    color = ElectricBlue
                )
            }

            items(state.questions, key = { it.id }) { q ->
                val chosen = state.selectedAnswers[q.id]
                val isCorrect = chosen == q.correctIndex
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (isCorrect) EmeraldContainer.copy(alpha = 0.55f) else RoseContainer.copy(alpha = 0.55f),
                    border = BorderStroke(
                        1.dp,
                        if (isCorrect) EmeraldSuccess else RoseError
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = q.question,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "إجابتك: ${chosen?.let { q.options.getOrNull(it) } ?: "لم تُجب"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isCorrect) EmeraldSuccess else RoseError
                        )
                        if (!isCorrect) {
                            Text(
                                text = "الإجابة الصحيحة: ${q.options[q.correctIndex]}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = EmeraldSuccess
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "التوضيح: ${q.explanation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private fun formatDuration(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.US, "%02d:%02d", mins, secs)
}
