package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
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
import com.example.ui.theme.EmeraldSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterDetailScreen(
    chapterNumber: Int,
    favoriteIds: Set<String>,
    onToggleFavorite: (String, FavoriteType, Int, String, String, Boolean) -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val chapter = remember(chapterNumber) { CurriculumRepository.getChapter(chapterNumber) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var activeSimulatorLaw by remember { mutableStateOf<PhysicsLaw?>(null) }

    if (chapter == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("الفصل غير موجود", color = MaterialTheme.colorScheme.onBackground)
        }
        return
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
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("chapter_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "رجوع",
                        tint = ElectricBlue
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "الفصل ${chapter.number}: ${chapter.title}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = chapter.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            SecondaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = ElectricBlue
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("ملخصات وكلاميات مركزة") },
                    modifier = Modifier.testTag("tab_summaries")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("قوانين ومسائل محلولة") },
                    modifier = Modifier.testTag("tab_laws_problems")
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (selectedTab == 0) {
                    // Definitions
                    item {
                        Text(
                            text = "التعاريف والمفاهيم الأساسية",
                            style = MaterialTheme.typography.titleMedium,
                            color = ElectricBlue
                        )
                    }
                    items(chapter.definitions, key = { it.id }) { def ->
                        val isFav = favoriteIds.contains(def.id)
                        GlassCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    SectionBadge(text = "تعريف")
                                    if (def.isMinisterial) {
                                        SectionBadge(text = "وزاري مهم", isGold = true)
                                    }
                                }
                                FavoriteToggleButton(
                                    isFavorited = isFav,
                                    onClick = {
                                        onToggleFavorite(
                                            def.id,
                                            FavoriteType.DEFINITION,
                                            chapter.number,
                                            def.title,
                                            def.content,
                                            isFav
                                        )
                                    },
                                    testTag = "fav_${def.id}"
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = def.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = CalmGold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = def.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Ministerial Reasonings
                    item {
                        Text(
                            text = "التعاليل الوزارية المهمة",
                            style = MaterialTheme.typography.titleMedium,
                            color = CalmGold
                        )
                    }
                    items(chapter.reasonings, key = { it.id }) { res ->
                        val isFav = favoriteIds.contains(res.id)
                        GlassCard(isGoldAccent = true) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SectionBadge(text = "علل وزارياً", isGold = true)
                                FavoriteToggleButton(
                                    isFavorited = isFav,
                                    onClick = {
                                        onToggleFavorite(
                                            res.id,
                                            FavoriteType.REASONING,
                                            chapter.number,
                                            res.question,
                                            res.answer,
                                            isFav
                                        )
                                    },
                                    testTag = "fav_${res.id}"
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "س: ${res.question}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "ج: ${res.answer}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = ElectricBlue
                            )
                        }
                    }

                    // Summaries & Comparisons
                    item {
                        Text(
                            text = "ملخصات ومقارنات مركزة",
                            style = MaterialTheme.typography.titleMedium,
                            color = ElectricBlue
                        )
                    }
                    items(chapter.summaries, key = { it.id }) { sum ->
                        GlassCard {
                            SectionBadge(text = sum.categoryLabel)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = sum.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            sum.points.forEach { point ->
                                Text(
                                    text = "• $point",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(vertical = 3.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Laws
                    item {
                        Text(
                            text = "قوانين الفصل ${chapter.number}",
                            style = MaterialTheme.typography.titleMedium,
                            color = ElectricBlue
                        )
                    }
                    items(chapter.laws, key = { it.id }) { law ->
                        val isFav = favoriteIds.contains(law.id)
                        GlassCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SectionBadge(text = "قانون رياضي", isGold = true)
                                FavoriteToggleButton(
                                    isFavorited = isFav,
                                    onClick = {
                                        onToggleFavorite(
                                            law.id,
                                            FavoriteType.LAW,
                                            chapter.number,
                                            law.name,
                                            "${law.formula}\n${law.variables}\n${law.unitNote}",
                                            isFav
                                        )
                                    },
                                    testTag = "fav_${law.id}"
                                )
                            }
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
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = { activeSimulatorLaw = law },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ElectricBlue,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    modifier = Modifier.testTag("sim_btn_${law.id}")
                                ) {
                                    Icon(Icons.Filled.Calculate, contentDescription = null)
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Text("احسب الناتج (محاكي القانون)")
                                }
                            }
                        }
                    }

                    // Solved Problems Step by Step
                    item {
                        Text(
                            text = "مسائل محلولة خطوة بخطوة",
                            style = MaterialTheme.typography.titleMedium,
                            color = CalmGold
                        )
                    }
                    items(chapter.solvedProblems, key = { it.id }) { prob ->
                        val isFav = favoriteIds.contains(prob.id)
                        GlassCard(isGoldAccent = true) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SectionBadge(
                                    text = if (prob.isMinisterial) "مسألة وزارية محلولة" else "مثال نموذجي",
                                    isGold = true
                                )
                                FavoriteToggleButton(
                                    isFavorited = isFav,
                                    onClick = {
                                        onToggleFavorite(
                                            prob.id,
                                            FavoriteType.PROBLEM,
                                            chapter.number,
                                            prob.title,
                                            "${prob.problemText}\nالقانون: ${prob.formulaUsed}\nالناتج: ${prob.finalAnswer}",
                                            isFav
                                        )
                                    },
                                    testTag = "fav_${prob.id}"
                                )
                            }
                            Text(
                                text = prob.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = prob.problemText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "المعطيات:",
                                style = MaterialTheme.typography.labelLarge,
                                color = CalmGold
                            )
                            prob.givenData.forEach { g ->
                                Text(
                                    text = "• $g",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "القانون المستخدم: ${prob.formulaUsed}",
                                style = MaterialTheme.typography.titleSmall,
                                color = ElectricBlue
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "خطوات الحل:",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            prob.steps.forEachIndexed { idx, step ->
                                Text(
                                    text = "${idx + 1}. $step",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "الناتج النهائي: ${prob.finalAnswer}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = EmeraldSuccess
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
