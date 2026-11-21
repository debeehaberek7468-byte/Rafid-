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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CurriculumRepository
import com.example.model.FavoriteType
import com.example.model.PhysicsLaw
import com.example.ui.components.FavoriteToggleButton
import com.example.ui.components.GlassCard
import com.example.ui.components.LawSimulatorSheet
import com.example.ui.components.SectionBadge
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer

@Composable
fun LawsBankScreen(
    favoriteIds: Set<String>,
    onToggleFavorite: (String, FavoriteType, Int, String, String, Boolean) -> Unit
) {
    var selectedChapter by remember { mutableIntStateOf(0) } // 0 = All
    var activeSimulatorLaw by remember { mutableStateOf<PhysicsLaw?>(null) }

    val filteredLaws = remember(selectedChapter) {
        if (selectedChapter == 0) {
            CurriculumRepository.allLaws
        } else {
            CurriculumRepository.allLaws.filter { it.chapterNumber == selectedChapter }
        }
    }

    activeSimulatorLaw?.let { law ->
        LawSimulatorSheet(
            law = law,
            onDismiss = { activeSimulatorLaw = null }
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
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "بنك القوانين والمحاكي الحسابي",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "أدخل معطيات أي مسألة في المحاكي للتحقق من صحة حلك خطوة بخطوة.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedChapter == 0,
                        onClick = { selectedChapter = 0 },
                        label = { Text("كل الفصول") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlueContainer,
                            selectedLabelColor = ElectricBlue
                        )
                    )
                }
                items(CurriculumRepository.chapters, key = { it.number }) { ch ->
                    FilterChip(
                        selected = selectedChapter == ch.number,
                        onClick = { selectedChapter = ch.number },
                        label = { Text("ف${ch.number}: ${ch.title}") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlueContainer,
                            selectedLabelColor = ElectricBlue
                        )
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredLaws, key = { it.id }) { law ->
                    val isFav = favoriteIds.contains(law.id)
                    GlassCard(isGoldAccent = law.simulatorType != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SectionBadge(
                                text = "الفصل ${law.chapterNumber}",
                                isGold = law.simulatorType != null
                            )
                            FavoriteToggleButton(
                                isFavorited = isFav,
                                onClick = {
                                    onToggleFavorite(
                                        law.id,
                                        FavoriteType.LAW,
                                        law.chapterNumber,
                                        law.name,
                                        "${law.formula}\n${law.variables}\n${law.unitNote}",
                                        isFav
                                    )
                                },
                                testTag = "fav_bank_${law.id}"
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = law.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = law.formula,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = ElectricBlue
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = law.variables,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = law.unitNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = CalmGold
                        )

                        if (law.simulatorType != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { activeSimulatorLaw = law },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("open_sim_${law.id}"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ElectricBlue,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(Icons.Filled.Calculate, contentDescription = null)
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(
                                    text = "احسب الناتج وتحقق من حلك",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
