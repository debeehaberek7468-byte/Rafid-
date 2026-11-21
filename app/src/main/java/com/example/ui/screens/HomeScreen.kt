package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.CurriculumRepository
import com.example.model.FavoriteType
import com.example.model.PhysicsChapter
import com.example.ui.components.FavoriteToggleButton
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionBadge
import com.example.ui.theme.CalmGold
import com.example.ui.theme.DeepSpaceNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer

@Composable
fun HomeScreen(
    favoriteIds: Set<String>,
    onToggleFavorite: (String, FavoriteType, Int, String, String, Boolean) -> Unit,
    onOpenChapter: (Int) -> Unit,
    onOpenTips: () -> Unit,
    onOpenAbout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by remember(searchQuery) {
        derivedStateOf { CurriculumRepository.searchCurriculum(searchQuery) }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_placeholder),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "بحث",
                            tint = ElectricBlue
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "مسح البحث"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("smart_search_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            if (searchQuery.isNotBlank()) {
                item {
                    Text(
                        text = "نتائج البحث (${searchResults.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = CalmGold
                    )
                }
                if (searchResults.isEmpty()) {
                    item {
                        GlassCard {
                            Text(
                                text = "لم يتم العثور على نتائج مطابقة لـ \"$searchQuery\". جرّب البحث بكلمات مثل: كولوم، أوم، التوازي، المحولة، الأوزون.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(searchResults, key = { it.id }) { res ->
                        val isFav = favoriteIds.contains(res.id)
                        GlassCard(
                            onClick = { onOpenChapter(res.chapterNumber) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SectionBadge(text = res.typeLabel, isGold = false)
                                FavoriteToggleButton(
                                    isFavorited = isFav,
                                    onClick = {
                                        onToggleFavorite(
                                            res.id,
                                            res.favoriteType,
                                            res.chapterNumber,
                                            res.title,
                                            res.fullContent,
                                            isFav
                                        )
                                    },
                                    testTag = "fav_search_${res.id}"
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = res.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = res.snippet,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            } else {
                // Hero Banner Card
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(176.dp)
                            .clip(RoundedCornerShape(22.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_physics_hero_banner_1795251097381),
                            contentDescription = "خلفية الفيزياء",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            DeepSpaceNavy.copy(alpha = 0.92f),
                                            DeepSpaceNavy.copy(alpha = 0.55f)
                                        )
                                    )
                                )
                                .padding(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.CenterStart),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                SectionBadge(text = "الأستاذ رافد فيصل", isGold = true)
                                Text(
                                    text = "فيزياء الثالث المتوسط",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = stringResource(R.string.motto_path_to_100),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ElectricBlue
                                )
                            }
                        }
                    }
                }

                // Quick Actions: Tips & About Prof
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AssistChip(
                            onClick = onOpenTips,
                            label = {
                                Text(
                                    text = "نصائح الامتحان الوزاري",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lightbulb,
                                    contentDescription = null,
                                    tint = CalmGold
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("open_tips_chip"),
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        AssistChip(
                            onClick = onOpenAbout,
                            label = {
                                Text(
                                    text = "حول الأستاذ رافد",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = null,
                                    tint = ElectricBlue
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("open_about_chip"),
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "فصول المنهج العراقي (9 فصول)",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            imageVector = Icons.Filled.Bolt,
                            contentDescription = null,
                            tint = CalmGold
                        )
                    }
                }

                items(CurriculumRepository.chapters, key = { it.number }) { chapter ->
                    ChapterCard(
                        chapter = chapter,
                        onClick = { onOpenChapter(chapter.number) }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun ChapterCard(
    chapter: PhysicsChapter,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("chapter_card_${chapter.number}"),
        isGoldAccent = chapter.number in listOf(1, 3, 5, 7),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(ElectricBlueContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${chapter.number}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = ElectricBlue
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "الفصل ${chapter.number}: ${chapter.title}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chapter.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionBadge(
                        text = chapter.badgeText,
                        isGold = chapter.number in listOf(1, 3, 5, 7)
                    )
                    SectionBadge(
                        text = "${chapter.laws.size} قوانين • ${chapter.solvedProblems.size} مسائل"
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "فتح الفصل",
                tint = CalmGold,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
