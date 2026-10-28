package com.brobot.future.ui.theme

import android.provider.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

object BrobotColors {
    val Bg = Color(0xFF07060D)
    val Surface = Color(0xFF120E24)
    val SurfaceHigh = Color(0xFF1B1535)
    val Violet = Color(0xFF8B5CF6)
    val VioletDeep = Color(0xFF3B2A78)
    val Lavender = Color(0xFFC4B5FD)
    val Cyan = Color(0xFF22D3EE)
    val Teal = Color(0xFF2DD4BF)
    val Amber = Color(0xFFFBBF24)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFA8B0C0)
}

private val BrobotScheme = darkColorScheme(
    primary = BrobotColors.Violet,
    onPrimary = Color.White,
    secondary = BrobotColors.Cyan,
    tertiary = BrobotColors.Teal,
    background = BrobotColors.Bg,
    onBackground = BrobotColors.TextPrimary,
    surface = BrobotColors.Surface,
    onSurface = BrobotColors.TextPrimary,
    surfaceVariant = BrobotColors.SurfaceHigh,
    onSurfaceVariant = BrobotColors.TextSecondary,
    outline = BrobotColors.VioletDeep,
    error = BrobotColors.Amber
)

val LocalMotionEnabled = staticCompositionLocalOf { true }

@Composable
fun rememberSystemAnimationsEnabled(): Boolean {
    val context = LocalContext.current

    return remember {
        try {
            Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.ANIMATOR_DURATION_SCALE,
                1f
            ) != 0f
        } catch (e: Exception) {
            true
        }
    }
}

@Composable
fun BrobotTheme(
    largeText: Boolean,
    reduceMotion: Boolean,
    content: @Composable () -> Unit
) {
    val base = LocalDensity.current

    val density = if (largeText) {
        Density(base.density, base.fontScale * 1.2f)
    } else {
        base
    }

    val motion = rememberSystemAnimationsEnabled() && !reduceMotion

    CompositionLocalProvider(
        LocalDensity provides density,
        LocalMotionEnabled provides motion
    ) {
        MaterialTheme(
            colorScheme = BrobotScheme,
            typography = Typography(),
            content = content
        )
    }
}
