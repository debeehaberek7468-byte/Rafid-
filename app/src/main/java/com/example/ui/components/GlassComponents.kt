package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CalmGold
import com.example.ui.theme.CalmGoldContainer
import com.example.ui.theme.DeepSpaceNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.GlassBorderCyan
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.GlassBorderSubtle
import com.example.ui.theme.GlassFillBottom
import com.example.ui.theme.GlassFillTop
import com.example.ui.theme.MidnightNavy
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CosmicBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(DeepSpaceNavy, MidnightNavy, DeepSpaceNavy)
                )
            )
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(ElectricBlue.copy(alpha = 0.14f), Color.Transparent),
                        center = Offset(size.width * 0.85f, size.height * 0.12f),
                        radius = size.minDimension * 0.65f
                    ),
                    radius = size.minDimension * 0.65f,
                    center = Offset(size.width * 0.85f, size.height * 0.12f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(CalmGold.copy(alpha = 0.08f), Color.Transparent),
                        center = Offset(size.width * 0.15f, size.height * 0.78f),
                        radius = size.minDimension * 0.55f
                    ),
                    radius = size.minDimension * 0.55f,
                    center = Offset(size.width * 0.15f, size.height * 0.78f)
                )
            },
        content = content
    )
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    isGoldAccent: Boolean = false,
    cornerRadius: Dp = 20.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    val borderColor = if (isGoldAccent) GlassBorderGold else GlassBorderCyan
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Surface(
        modifier = modifier
            .clip(shape)
            .then(clickableModifier),
        shape = shape,
        color = Color.Transparent,
        border = BorderStroke(1.dp, borderColor),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(GlassFillTop, GlassFillBottom)
                    )
                )
                .padding(16.dp),
            content = content
        )
    }
}

@Composable
fun SectionBadge(
    text: String,
    isGold: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isGold) CalmGoldContainer else ElectricBlueContainer
    val textColor = if (isGold) CalmGold else ElectricBlue
    val border = if (isGold) GlassBorderGold else GlassBorderCyan

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = bgColor,
        border = BorderStroke(0.8.dp, border)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = textColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun FavoriteToggleButton(
    isFavorited: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = if (isFavorited) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = if (isFavorited) "إزالة من المفضلة" else "حفظ في المفضلة",
            tint = if (isFavorited) CalmGold else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AnimatedAtomWaveLogo(
    modifier: Modifier = Modifier,
    showWave: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "atom_wave")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbital_rotation"
    )
    val wavePhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase"
    )
    val pulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nucleus_pulse"
    )

    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val orbitRx = size.minDimension * 0.42f
        val orbitRy = size.minDimension * 0.16f

        // Subtle outer halo
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(ElectricBlue.copy(alpha = 0.24f), Color.Transparent),
                center = Offset(cx, cy),
                radius = size.minDimension * 0.48f
            ),
            radius = size.minDimension * 0.48f,
            center = Offset(cx, cy)
        )

        if (showWave) {
            val wavePath = Path()
            val waveAmp = size.height * 0.08f
            val steps = 40
            for (i in 0..steps) {
                val fraction = i / steps.toFloat()
                val x = fraction * size.width
                val y = cy + sin(fraction * 4f * Math.PI.toFloat() + wavePhase) * waveAmp
                if (i == 0) wavePath.moveTo(x, y) else wavePath.lineTo(x, y)
            }
            drawPath(
                path = wavePath,
                color = CalmGold.copy(alpha = 0.42f),
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Three rotated elliptical atomic orbitals
        val orbitAngles = listOf(0f, 60f, 120f)
        orbitAngles.forEachIndexed { idx, baseDeg ->
            rotate(degrees = baseDeg + angle * if (idx % 2 == 0) 1f else -0.7f, pivot = Offset(cx, cy)) {
                drawOval(
                    color = if (idx == 1) CalmGold.copy(alpha = 0.78f) else ElectricBlue.copy(alpha = 0.85f),
                    topLeft = Offset(cx - orbitRx, cy - orbitRy),
                    size = androidx.compose.ui.geometry.Size(orbitRx * 2f, orbitRy * 2f),
                    style = Stroke(width = 2.2.dp.toPx())
                )
                // Moving electron dot
                val rad = Math.toRadians((angle * 2.0 + idx * 120.0))
                val ex = cx + (orbitRx * cos(rad)).toFloat()
                val ey = cy + (orbitRy * sin(rad)).toFloat()
                drawCircle(
                    color = if (idx == 1) CalmGold else ElectricBlue,
                    radius = 4.dp.toPx(),
                    center = Offset(ex, ey)
                )
            }
        }

        // Glowing nucleus
        drawCircle(
            color = CalmGold.copy(alpha = 0.35f),
            radius = 12.dp.toPx() * pulse,
            center = Offset(cx, cy)
        )
        drawCircle(
            color = ElectricBlue,
            radius = 7.dp.toPx(),
            center = Offset(cx, cy)
        )
    }
}

@Composable
fun SubtleGlassDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(GlassBorderSubtle)
    )
}
