package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.auth.GoogleSignInButton
import com.example.ui.components.AnimatedAtomWaveLogo
import com.example.ui.components.CosmicBackground
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionBadge
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.RoseError
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1750L)
        onFinished()
    }

    CosmicBackground(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedAtomWaveLogo(
                modifier = Modifier.size(190.dp),
                showWave = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { it / 3 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionBadge(
                        text = stringResource(R.string.subject_subtitle),
                        isGold = true
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = stringResource(R.string.professor_full_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.motto_path_to_100),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                        color = ElectricBlue,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }

    CosmicBackground(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.widthIn(max = 520.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_prof_rafid_avatar_1795251097808),
                    contentDescription = "صورة الأستاذ رافد فيصل",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(124.dp)
                        .clip(CircleShape)
                        .border(2.dp, CalmGold, CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionBadge(text = "المنهج العراقي المعتمد", isGold = true)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.professor_full_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.subject_subtitle),
                    style = MaterialTheme.typography.titleMedium,
                    color = ElectricBlue,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.motto_path_to_100),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CalmGold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                GlassCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        FeatureHighlightRow(
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            title = "شرح الفصول التسعة كاملة",
                            subtitle = "تعاريف، تعاليل وزارية، ومسائل محلولة خطوة بخطوة"
                        )
                        FeatureHighlightRow(
                            icon = Icons.Filled.Calculate,
                            title = "محاكي القوانين التفاعلي",
                            subtitle = "احسب الناتج وتحقق من صحة حلك لقانون كولوم وأوم والمحولة"
                        )
                        FeatureHighlightRow(
                            icon = Icons.Filled.Quiz,
                            title = "اختبارات ذكية + اختبار وزاري شامل",
                            subtitle = "تصحيح فوري مع توضيح السبب ومزامنة تقدمك وملاحظاتك"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.sign_in_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                GoogleSignInButton(
                    onAuthSuccess = {
                        errorMessage = null
                        onAuthSuccess()
                    },
                    onAuthError = { msg -> errorMessage = msg }
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    GlassCard(isGoldAccent = true) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = RoseError
                            )
                            Text(
                                text = errorMessage!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = RoseError
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureHighlightRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ElectricBlue,
            modifier = Modifier.size(26.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
