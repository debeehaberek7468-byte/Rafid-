package com.example.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

enum class FavoriteType(val arabicLabel: String) {
    DEFINITION("تعريف"),
    REASONING("تعليل"),
    LAW("قانون"),
    PROBLEM("مسألة محلولة");

    companion object {
        fun fromRaw(raw: String): FavoriteType =
            entries.firstOrNull { it.name == raw } ?: DEFINITION
    }
}

enum class NoteCategory(val arabicLabel: String) {
    SUMMARY("ملخص"),
    LAW("قانون"),
    EXAM_TIP("تنبيه وزاري"),
    REMINDER("تذكير");

    companion object {
        fun fromRaw(raw: String): NoteCategory =
            entries.firstOrNull { it.name == raw } ?: SUMMARY
    }
}

@Keep
@IgnoreExtraProperties
data class FavoriteItem(
    @DocumentId val id: String = "",
    val userId: String = "",
    val itemId: String = "",
    val itemType: String = FavoriteType.DEFINITION.name,
    val chapterNumber: Long = 1L,
    val title: String = "",
    val content: String = "",
    val extraNote: String? = null,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class StudyNote(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val body: String = "",
    val chapterNumber: Long = 0L,
    val category: String = NoteCategory.SUMMARY.name,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class QuizResult(
    @DocumentId val id: String = "",
    val userId: String = "",
    val quizTitle: String = "",
    val chapterNumber: Long = 0L,
    val score: Long = 0L,
    val totalQuestions: Long = 1L,
    val durationSeconds: Long = 0L,
    @ServerTimestamp val createdAt: Timestamp? = null
)

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
