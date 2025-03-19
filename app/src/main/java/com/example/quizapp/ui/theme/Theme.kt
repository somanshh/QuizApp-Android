// ui/theme/Theme.kt
package com.example.quizapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF006492),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC9E6FF),
    onPrimaryContainer = Color(0xFF001E30),
    secondary = Color(0xFF4D616C),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD0E6F2),
    onSecondaryContainer = Color(0xFF081E27),
    tertiary = Color(0xFF605A7D),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE6DEFF),
    onTertiaryContainer = Color(0xFF1C1736),
    background = Color.White,
    onBackground = Color(0xFF191C1E),
    surface = Color(0xFFFBFCFE),
    onSurface = Color(0xFF191C1E)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8BCDFF),
    onPrimary = Color(0xFF00344F),
    primaryContainer = Color(0xFF004B70),
    onPrimaryContainer = Color(0xFFC9E6FF),
    secondary = Color(0xFFB4CAD6),
    onSecondary = Color(0xFF1F333D),
    secondaryContainer = Color(0xFF354A54),
    onSecondaryContainer = Color(0xFFD0E6F2),
    tertiary = Color(0xFFC9BFFF),
    onTertiary = Color(0xFF322C4C),
    tertiaryContainer = Color(0xFF494264),
    onTertiaryContainer = Color(0xFFE6DEFF),
    background = Color(0xFF191C1E),
    onBackground = Color(0xFFE1E2E5),
    surface = Color(0xFF111417),
    onSurface = Color(0xFFC5C6C9)
)

@Composable
fun QuizAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}