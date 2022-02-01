package jp.mydns.kokoichi0206.countdowntimer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * アプリ内で使用するComposable関数のダークモードのテーマ。
 */
private val DarkColorPalette = darkColors(
    primary = Color(0xff4cd196),
    primaryVariant = Purple700,
    secondary = Color(0xff8cc4cc),
    background = Color(0xFF11130F),
    onSurface = Color(0xFF445444),
    surface = Color(0xFF888888),
)

/**
 * アプリ内で使用するComposable関数のテーマ。
 */
@Composable
fun CountDownTimerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}