package com.cristobal.tcgtableorganizerv1.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ------------------------ PALETA CLARA ------------------------
private val LightColors = lightColorScheme(
    primary = Burgundy,
    onPrimary = White,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,   // **negro sobre fondo**

    surface = SurfaceLight,
    onSurface = TextPrimaryLight,      // **negro en tarjetas**

    surfaceVariant = SurfaceLightAlt,
    onSurfaceVariant = TextSecondaryLight,

    secondary = BurgundySoft,
    onSecondary = White
)

// ------------------------ PALETA OSCURA ------------------------
private val DarkColors = darkColorScheme(
    primary = Burgundy,
    onPrimary = White,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = SurfaceDark,
    onSurface = TextPrimaryDark,

    surfaceVariant = SurfaceDarkAlt,
    onSurfaceVariant = TextSecondaryDark,

    secondary = BurgundySoft,
    onSecondary = White
)

@Composable
fun TCGTableOrganizerV1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
