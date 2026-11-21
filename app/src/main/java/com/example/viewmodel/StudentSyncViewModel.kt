package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.StudentDataRepository
import com.example.model.FavoriteItem
import com.example.model.FavoriteType
import com.example.model.NoteCategory
import com.example.model.QuizResult
import com.example.model.StudyNote
import com.example.model.UiState
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentSyncViewModel(
    private val repository: StudentDataRepository,
    private val currentUserId: String
) : ViewModel() {

    private val _feedbackMessages = MutableSharedFlow<String>(extraBufferCapacity = 4)
    val feedbackMessages: SharedFlow<String> = _feedbackMessages.asSharedFlow()

    val favoritesState: StateFlow<UiState<List<FavoriteItem>>> = repository
        .observeFavorites(currentUserId)
        .map<List<FavoriteItem>, UiState<List<FavoriteItem>>> { UiState.Success(it) }
        .catch { error ->
            val firestoreError = generateSequence(error) { it.cause }
                .filterIsInstance<FirebaseFirestoreException>()
                .firstOrNull()
            if (firestoreError == null && error is CancellationException) throw error
            val rootError = firestoreError ?: error
            Log.w(TAG, "Error observing favorites", rootError)
            emit(UiState.Error(rootError.message ?: "تعذر تحميل المفضلة"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = UiState.Loading
        )

    val notesState: StateFlow<UiState<List<StudyNote>>> = repository
        .observeNotes(currentUserId)
        .map<List<StudyNote>, UiState<List<StudyNote>>> { UiState.Success(it) }
        .catch { error ->
            val firestoreError = generateSequence(error) { it.cause }
                .filterIsInstance<FirebaseFirestoreException>()
                .firstOrNull()
            if (firestoreError == null && error is CancellationException) throw error
            val rootError = firestoreError ?: error
            Log.w(TAG, "Error observing notes", rootError)
            emit(UiState.Error(rootError.message ?: "تعذر تحميل الملاحظات"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = UiState.Loading
        )

    val quizResultsState: StateFlow<UiState<List<QuizResult>>> = repository
        .observeQuizResults(currentUserId)
        .map<List<QuizResult>, UiState<List<QuizResult>>> { UiState.Success(it) }
        .catch { error ->
            val firestoreError = generateSequence(error) { it.cause }
                .filterIsInstance<FirebaseFirestoreException>()
                .firstOrNull()
            if (firestoreError == null && error is CancellationException) throw error
            val rootError = firestoreError ?: error
            Log.w(TAG, "Error observing quiz results", rootError)
            emit(UiState.Error(rootError.message ?: "تعذر تحميل سجل الاختبارات"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = UiState.Loading
        )

    fun toggleFavorite(
        itemId: String,
        type: FavoriteType,
        chapterNumber: Int,
        title: String,
        content: String,
        currentlyFavorited: Boolean
    ) {
        viewModelScope.launch {
            val result = if (currentlyFavorited) {
                repository.removeFavorite(itemId)
            } else {
                repository.addFavorite(
                    itemId = itemId,
                    type = type,
                    chapterNumber = chapterNumber,
                    title = title,
                    content = content
                )
            }
            result.onSuccess {
                _feedbackMessages.tryEmit(
                    if (currentlyFavorited) "تمت الإزالة من المفضلة" else "تم الحفظ في المفضلة للمراجعة"
                )
            }.onFailure { err ->
                _feedbackMessages.tryEmit(err.localizedMessage ?: "تعذر تحديث المفضلة")
            }
        }
    }

    fun removeFavorite(itemId: String) {
        viewModelScope.launch {
            repository.removeFavorite(itemId)
                .onSuccess { _feedbackMessages.tryEmit("تمت إزالة العنصر من المفضلة") }
                .onFailure { err ->
                    _feedbackMessages.tryEmit(err.localizedMessage ?: "تعذر حذف العنصر")
                }
        }
    }

    fun saveNote(
        existingNoteId: String?,
        title: String,
        body: String,
        chapterNumber: Int,
        category: NoteCategory
    ) {
        if (title.isBlank() || body.isBlank()) {
            _feedbackMessages.tryEmit("يرجى كتابة عنوان ومحتوى الملاحظة")
            return
        }
        viewModelScope.launch {
            val result = if (existingNoteId.isNullOrBlank()) {
                repository.addNote(title, body, chapterNumber, category)
            } else {
                repository.updateNote(existingNoteId, title, body, chapterNumber, category)
            }
            result.onSuccess {
                _feedbackMessages.tryEmit(
                    if (existingNoteId.isNullOrBlank()) "تمت إضافة الملاحظة" else "تم تحديث الملاحظة"
                )
            }.onFailure { err ->
                _feedbackMessages.tryEmit(err.localizedMessage ?: "تعذر حفظ الملاحظة")
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
                .onSuccess { _feedbackMessages.tryEmit("تم حذف الملاحظة") }
                .onFailure { err ->
                    _feedbackMessages.tryEmit(err.localizedMessage ?: "تعذر حذف الملاحظة")
                }
        }
    }

    fun recordQuizScore(
        quizTitle: String,
        chapterNumber: Int,
        score: Int,
        totalQuestions: Int,
        durationSeconds: Int
    ) {
        viewModelScope.launch {
            repository.saveQuizResult(
                quizTitle = quizTitle,
                chapterNumber = chapterNumber,
                score = score,
                totalQuestions = totalQuestions,
                durationSeconds = durationSeconds
            ).onFailure { err ->
                _feedbackMessages.tryEmit(err.localizedMessage ?: "تعذر حفظ نتيجة الاختبار")
            }
        }
    }

    private companion object {
        const val TAG = "StudentSyncVM"
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}
