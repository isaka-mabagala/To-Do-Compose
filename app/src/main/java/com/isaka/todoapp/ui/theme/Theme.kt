package com.isaka.todoapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4264FA),
    onPrimary = Color(0xFFFCFCFC),
    secondary = Color(0xFFE8F0FE),
    onSecondary = Color(0xFF141414),
    tertiary = Color(0xFF4264FA),
    onTertiary = Color(0xFFFCFCFC),
    background = Color(0xFF303132),
    onBackground = Color(0xFFFCFCFC),
    surface = Color(0xFF141414),
    onSurface = Color(0xFFFCFCFC),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4264FA),
    onPrimary = Color(0xFFFCFCFC),
    secondary = Color(0xFFE8F0FE),
    onSecondary = Color(0xFF141414),
    tertiary = Color(0xFF000000),
    onTertiary = Color(0xFFFCFCFC),
    background = Color(0xFFEDEDED),
    onBackground = Color(0xFF141414),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF141414)
)

@Composable
fun ToDoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
