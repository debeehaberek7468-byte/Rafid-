package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import com.example.R
import com.example.auth.signOutUser
import com.example.data.CurriculumRepository
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionBadge
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.RoseError
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun TipsScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("tips_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "رجوع",
                        tint = ElectricBlue
                    )
                }
                Text(
                    text = stringResource(R.string.ministerial_tips_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    GlassCard(isGoldAccent = true) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Lightbulb,
                                contentDescription = null,
                                tint = CalmGold,
                                modifier = Modifier.size(30.dp)
                            )
                            Column {
                                Text(
                                    text = "وصايا ذهبية قبل دخول القاعة الامتحانية",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = CalmGold
                                )
                                Text(
                                    text = "إعداد الأستاذ رافد فيصل لضمان الدرجة الكاملة",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                items(CurriculumRepository.ministerialTips, key = { it.id }) { tip ->
                    GlassCard {
                        SectionBadge(text = tip.category, isGold = tip.id in listOf("tip_2", "tip_3"))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tip.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = tip.details,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onShowMessage: (String) -> Unit
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }
    val user = Firebase.auth.currentUser

    fun openExternalLink(url: String, platformName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (_: Exception) {
            onShowMessage("تعذر فتح تطبيق $platformName على هذا الجهاز")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("about_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "رجوع",
                        tint = ElectricBlue
                    )
                }
                Text(
                    text = stringResource(R.string.about_professor_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_prof_rafid_avatar_1795251097808),
                    contentDescription = "الأستاذ رافد فيصل",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(132.dp)
                        .clip(CircleShape)
                        .border(2.5.dp, CalmGold, CircleShape)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(R.string.professor_full_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "موثق",
                        tint = ElectricBlue
                    )
                }

                Text(
                    text = "مدرس مادة الفيزياء للمرحلة المتوسطة والإعدادية",
                    style = MaterialTheme.typography.titleMedium,
                    color = CalmGold,
                    textAlign = TextAlign.Center
                )

                GlassCard {
                    Text(
                        text = "الرسالة التعليمية",
                        style = MaterialTheme.typography.titleMedium,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "نسعى في هذا التطبيق إلى تبسيط مفاهيم فيزياء الثالث المتوسط للمنهج العراقي بأسلوب عصري منظم يجمع بين الملخصات المركزة، القوانين التفاعلية، والاختبارات الوزارية الذكية؛ ليكون رفيق الطالب في طريقه نحو الـ 100.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Quick Social Links (Telegram & TikTok)
                GlassCard(isGoldAccent = true) {
                    Text(
                        text = "تواصل مع الأستاذ رافد فيصل",
                        style = MaterialTheme.typography.titleMedium,
                        color = CalmGold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { openExternalLink("https://t.me/", "تيليجرام") },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("telegram_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlue,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(stringResource(R.string.telegram_channel))
                        }

                        OutlinedButton(
                            onClick = { openExternalLink("https://www.tiktok.com/", "تيك توك") },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("tiktok_button")
                        ) {
                            Icon(Icons.Filled.MusicNote, contentDescription = null, tint = CalmGold)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = stringResource(R.string.tiktok_account),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Student Google Account & Sign-Out Card
                GlassCard {
                    Text(
                        text = "حساب الطالب المتزامن",
                        style = MaterialTheme.typography.titleSmall,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = user?.displayName?.takeIf { it.isNotBlank() } ?: "طالب فيزياء الثالث المتوسط",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    user?.email?.let { email ->
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = {
                            signOutUser(
                                credentialManager = credentialManager,
                                scope = scope
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("sign_out_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                            tint = RoseError
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = stringResource(R.string.sign_out),
                            color = RoseError
                        )
                    }
                }
            }
        }
    }
}
