package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CurriculumRepository
import com.example.model.QuizQuestion
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ActiveQuizUiState(
    val isActive: Boolean = false,
    val isFinished: Boolean = false,
    val chapterNumber: Int = 0,
    val quizTitle: String = "",
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswers: Map<String, Int> = emptyMap(),
    val elapsedSeconds: Int = 0
) {
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentIndex)

    val score: Int
        get() = questions.count { q -> selectedAnswers[q.id] == q.correctIndex }
}

class QuizViewModel : ViewModel() {
    private val _state = MutableStateFlow(ActiveQuizUiState())
    val state: StateFlow<ActiveQuizUiState> = _state.asStateFlow()

    private var timerJob: Job? = null

    fun startQuiz(chapterNumber: Int, quizTitle: String) {
        val questions = CurriculumRepository.getQuestionsForQuiz(chapterNumber)
        timerJob?.cancel()
        _state.value = ActiveQuizUiState(
            isActive = true,
            isFinished = false,
            chapterNumber = chapterNumber,
            quizTitle = quizTitle,
            questions = questions,
            currentIndex = 0,
            selectedAnswers = emptyMap(),
            elapsedSeconds = 0
        )
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _state.update { current ->
                    if (current.isActive && !current.isFinished) {
                        current.copy(elapsedSeconds = current.elapsedSeconds + 1)
                    } else current
                }
            }
        }
    }

    fun selectAnswer(questionId: String, optionIndex: Int) {
        _state.update { current ->
            current.copy(selectedAnswers = current.selectedAnswers + (questionId to optionIndex))
        }
    }

    fun nextQuestion() {
        _state.update { current ->
            val maxIndex = (current.questions.size - 1).coerceAtLeast(0)
            current.copy(currentIndex = (current.currentIndex + 1).coerceAtMost(maxIndex))
        }
    }

    fun previousQuestion() {
        _state.update { current ->
            current.copy(currentIndex = (current.currentIndex - 1).coerceAtLeast(0))
        }
    }

    fun finishQuiz(onCompleted: (quizTitle: String, chapterNumber: Int, score: Int, total: Int, duration: Int) -> Unit) {
        timerJob?.cancel()
        val snapshot = _state.value
        _state.update { it.copy(isFinished = true) }
        onCompleted(
            snapshot.quizTitle,
            snapshot.chapterNumber,
            snapshot.score,
            snapshot.questions.size,
            snapshot.elapsedSeconds
        )
    }

    fun exitQuiz() {
        timerJob?.cancel()
        _state.value = ActiveQuizUiState()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
