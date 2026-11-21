package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.FavoriteItem
import com.example.model.FavoriteType
import com.example.model.NoteCategory
import com.example.model.StudyNote
import com.example.model.UiState
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionBadge
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.RoseError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesNotesScreen(
    favoritesState: UiState<List<FavoriteItem>>,
    notesState: UiState<List<StudyNote>>,
    onRemoveFavorite: (String) -> Unit,
    onSaveNote: (noteId: String?, title: String, body: String, chapterNumber: Int, category: NoteCategory) -> Unit,
    onDeleteNote: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var editingNote by remember { mutableStateOf<StudyNote?>(null) }
    var showNoteDialog by remember { mutableStateOf(false) }

    if (showNoteDialog) {
        NoteEditorDialog(
            initialNote = editingNote,
            onDismiss = {
                showNoteDialog = false
                editingNote = null
            },
            onSave = { title, body, chapterNum, category ->
                onSaveNote(editingNote?.id, title, body, chapterNum, category)
                showNoteDialog = false
                editingNote = null
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
        ) {
            SecondaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = ElectricBlue
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("المفضلة") },
                    modifier = Modifier.testTag("tab_favorites")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("ملاحظاتي للامتحان") },
                    modifier = Modifier.testTag("tab_notes")
                )
            }

            if (selectedTab == 0) {
                FavoritesTabContent(
                    favoritesState = favoritesState,
                    onRemoveFavorite = onRemoveFavorite
                )
            } else {
                NotesTabContent(
                    notesState = notesState,
                    onEditNote = { note ->
                        editingNote = note
                        showNoteDialog = true
                    },
                    onDeleteNote = onDeleteNote
                )
            }
        }

        if (selectedTab == 1) {
            FloatingActionButton(
                onClick = {
                    editingNote = null
                    showNoteDialog = true
                },
                containerColor = CalmGold,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .testTag("add_note_fab")
            ) {
                Icon(Icons.Filled.Add, contentDescription = "إضافة ملاحظة جديدة")
            }
        }
    }
}

@Composable
private fun FavoritesTabContent(
    favoritesState: UiState<List<FavoriteItem>>,
    onRemoveFavorite: (String) -> Unit
) {
    when (favoritesState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = ElectricBlue)
            }
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                GlassCard {
                    Text(favoritesState.message, color = RoseError)
                }
            }
        }
        is UiState.Success -> {
            if (favoritesState.data.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
                    GlassCard {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.NoteAlt,
                                contentDescription = null,
                                tint = CalmGold,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "قائمة المفضلة فارغة",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "اضغط على أيقونة الحفظ بجانب أي تعريف أو تعليل أو قانون أو مسألة لإضافتها هنا للمراجعة السريعة ليلة الامتحان.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favoritesState.data, key = { it.id }) { item ->
                        val type = FavoriteType.fromRaw(item.itemType)
                        GlassCard(isGoldAccent = type == FavoriteType.LAW || type == FavoriteType.PROBLEM) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SectionBadge(
                                    text = "${type.arabicLabel} • الفصل ${item.chapterNumber}",
                                    isGold = type == FavoriteType.REASONING || type == FavoriteType.LAW
                                )
                                IconButton(
                                    onClick = { onRemoveFavorite(item.itemId.ifEmpty { item.id }) },
                                    modifier = Modifier.testTag("remove_fav_${item.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.BookmarkRemove,
                                        contentDescription = "إزالة من المفضلة",
                                        tint = RoseError
                                    )
                                }
                            }
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NotesTabContent(
    notesState: UiState<List<StudyNote>>,
    onEditNote: (StudyNote) -> Unit,
    onDeleteNote: (String) -> Unit
) {
    when (notesState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = ElectricBlue)
            }
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                GlassCard {
                    Text(notesState.message, color = RoseError)
                }
            }
        }
        is UiState.Success -> {
            if (notesState.data.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
                    GlassCard {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.NoteAlt,
                                contentDescription = null,
                                tint = ElectricBlue,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "لا توجد ملاحظات مسجلة بعد",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "اضغط على زر (+) في الأسفل لتدوين ملاحظاتك وتنبيهاتك المهمة قبل الامتحان الوزاري.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 84.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(notesState.data, key = { it.id }) { note ->
                        val cat = NoteCategory.fromRaw(note.category)
                        GlassCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val chLabel = if (note.chapterNumber == 0L) "عام" else "الفصل ${note.chapterNumber}"
                                SectionBadge(
                                    text = "${cat.arabicLabel} • $chLabel",
                                    isGold = cat == NoteCategory.EXAM_TIP
                                )
                                Row {
                                    IconButton(onClick = { onEditNote(note) }) {
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = "تعديل الملاحظة",
                                            tint = ElectricBlue
                                        )
                                    }
                                    IconButton(onClick = { onDeleteNote(note.id) }) {
                                        Icon(
                                            imageVector = Icons.Filled.DeleteOutline,
                                            contentDescription = "حذف الملاحظة",
                                            tint = RoseError
                                        )
                                    }
                                }
                            }
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = note.body,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteEditorDialog(
    initialNote: StudyNote?,
    onDismiss: () -> Unit,
    onSave: (title: String, body: String, chapterNumber: Int, category: NoteCategory) -> Unit
) {
    var title by remember { mutableStateOf(initialNote?.title ?: "") }
    var body by remember { mutableStateOf(initialNote?.body ?: "") }
    var chapterNumber by remember { mutableIntStateOf(initialNote?.chapterNumber?.toInt() ?: 0) }
    var category by remember {
        mutableStateOf(
            initialNote?.let { NoteCategory.fromRaw(it.category) } ?: NoteCategory.SUMMARY
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialNote == null) "ملاحظة دراسية جديدة" else "تعديل الملاحظة",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { if (it.length <= 120) title = it },
                    label = { Text("عنوان الملاحظة") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("note_title_input")
                )
                OutlinedTextField(
                    value = body,
                    onValueChange = { if (it.length <= 2000) body = it },
                    label = { Text("التفاصيل أو القاعدة المهمة") },
                    minLines = 3,
                    maxLines = 6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("note_body_input")
                )
                Text("التصنيف:", style = MaterialTheme.typography.labelMedium, color = CalmGold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(NoteCategory.entries) { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat.arabicLabel) }
                        )
                    }
                }
                Text("الفصل:", style = MaterialTheme.typography.labelMedium, color = ElectricBlue)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items((0..9).toList()) { num ->
                        FilterChip(
                            selected = chapterNumber == num,
                            onClick = { chapterNumber = num },
                            label = { Text(if (num == 0) "عام" else "ف$num") }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, body, chapterNumber, category) },
                enabled = title.isNotBlank() && body.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                modifier = Modifier.testTag("save_note_button")
            ) {
                Text("حفظ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
