package com.example.crmmeandroid.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.heros.ui.theme.md_theme_dark_background
import com.example.heros.ui.theme.md_theme_dark_onPrimary
import com.example.heros.ui.theme.md_theme_dark_onSurface
import com.example.heros.ui.theme.md_theme_dark_primary
import com.example.heros.ui.theme.md_theme_dark_secondary
import com.example.heros.ui.theme.md_theme_dark_surface
import com.example.heros.ui.theme.md_theme_light_background
import com.example.heros.ui.theme.md_theme_light_onPrimary
import com.example.heros.ui.theme.md_theme_light_onSurface
import com.example.heros.ui.theme.md_theme_light_primary
import com.example.heros.ui.theme.md_theme_light_secondary
import com.example.heros.ui.theme.md_theme_light_surface

private val DarkColorPalette = darkColors(
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary
)

private val LightColorPalette = lightColors(
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content,
        typography = Typography(),
        shapes = Shapes()
    )
}