package com.brobot.future.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brobot.future.ui.components.DemoStatusBadge
import com.brobot.future.ui.components.PrimaryActionButton
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.LocalMotionEnabled
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun IntroScreen(
    onEnter: () -> Unit,
    onExploreDemo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(16.dp))

        ShieldConstellation(Modifier.size(220.dp))

        Spacer(Modifier.height(24.dp))

        Text(
            "BROBOT",
            fontSize = 44.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 8.sp,
            color = BrobotColors.TextPrimary
        )

        Text(
            "2035",
            fontSize = 18.sp,
            letterSpacing = 6.sp,
            color = BrobotColors.Lavender
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "YOUR DIGITAL LIFE. UNDER YOUR CONTROL.",
            style = MaterialTheme.typography.labelLarge,
            letterSpacing = 1.5.sp,
            color = BrobotColors.Cyan,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Explore the future of personal privacy.",
            style = MaterialTheme.typography.bodyLarge,
            color = BrobotColors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        DemoStatusBadge("CONCEPT PROTOTYPE · SYNTHETIC DATA")

        Spacer(Modifier.height(28.dp))

        PrimaryActionButton(
            "Enter BROBOT",
            onEnter,
            Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        PrimaryActionButton(
            "Explore Demo",
            onExploreDemo,
            Modifier.fillMaxWidth(),
            secondary = true
        )
    }
}

@Composable
private fun ShieldConstellation(modifier: Modifier) {
    val motion = LocalMotionEnabled.current

    val angle: Float = if (motion) {
        rememberInfiniteTransition(label = "intro").animateFloat(
            0f,
            360f,
            infiniteRepeatable(
                tween(24000, easing = LinearEasing),
                RepeatMode.Restart
            ),
            label = "angle"
        ).value
    } else {
        0f
    }

    val pulse: Float = if (motion) {
        rememberInfiniteTransition(label = "pulse").animateFloat(
            0.5f,
            1f,
            infiniteRepeatable(tween(2200), RepeatMode.Reverse),
            label = "pulseValue"
        ).value
    } else {
        0.8f
    }

    Canvas(modifier) {
        val c = Offset(size.width / 2, size.height / 2)
        val r = size.minDimension / 2

        drawCircle(
            BrobotColors.VioletDeep,
            radius = r * 0.95f,
            center = c,
            style = Stroke(width = 2.dp.toPx())
        )

        for (i in 0 until 7) {
            val a = Math.toRadians(angle + i * 360.0 / 7)
            val p = Offset(
                c.x + (r * 0.95f * cos(a)).toFloat(),
                c.y + (r * 0.95f * sin(a)).toFloat()
            )

            drawCircle(
                BrobotColors.Lavender.copy(alpha = pulse),
                radius = 4.dp.toPx(),
                center = p
            )
        }

        val s = Path().apply {
            moveTo(c.x, c.y - r * 0.55f)
            lineTo(c.x + r * 0.42f, c.y - r * 0.38f)
            lineTo(c.x + r * 0.42f, c.y + r * 0.05f)

            cubicTo(
                c.x + r * 0.42f, c.y + r * 0.35f,
                c.x + r * 0.2f, c.y + r * 0.5f,
                c.x, c.y + r * 0.62f
            )

            cubicTo(
                c.x - r * 0.2f, c.y + r * 0.5f,
                c.x - r * 0.42f, c.y + r * 0.35f,
                c.x - r * 0.42f, c.y + r * 0.05f
            )

            lineTo(c.x - r * 0.42f, c.y - r * 0.38f)
            close()
        }

        drawPath(
            s,
            BrobotColors.Violet.copy(alpha = 0.18f)
        )

        drawPath(
            s,
            BrobotColors.Violet,
            style = Stroke(width = 3.dp.toPx())
        )

        val tick = Path().apply {
            moveTo(c.x - r * 0.2f, c.y + r * 0.02f)
            lineTo(c.x - r * 0.04f, c.y + r * 0.18f)
            lineTo(c.x + r * 0.22f, c.y - r * 0.14f)
        }

        drawPath(
            tick,
            BrobotColors.Teal.copy(alpha = pulse),
            style = Stroke(width = 4.dp.toPx())
        )
    }
}
