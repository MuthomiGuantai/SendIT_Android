// app/src/main/java/com/example/sendit/ui/theme/Theme.kt

package com.example.sendit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC), // Purple 200
    onPrimary = Color(0xFF000000), // Black
    primaryContainer = Color(0xFF3700B3), // Purple 700
    secondary = Color(0xFF03DAC5), // Teal 200
    onSecondary = Color(0xFF000000), // Black
    background = Color(0xFF121212), // Dark background
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Purple 500
    onPrimary = Color(0xFFFFFFFF), // White
    primaryContainer = Color(0xFF3700B3), // Purple 700
    secondary = Color(0xFF03DAC5), // Teal 200
    onSecondary = Color(0xFF000000), // Black
    background = Color(0xFFFFFFFF), // White background
    surface = Color(0xFFF5F5F5),
    onSurface = Color(0xFF000000)
)

@Composable
fun SendITTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}