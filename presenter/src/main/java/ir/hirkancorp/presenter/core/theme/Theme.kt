package ir.hirkancorp.presenter.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import ir.hirkancorp.presenter.core.Dimensions
import ir.hirkancorp.presenter.core.LocalSpacing
import ir.hirkancorp.user.ui.theme.Shapes
import ir.hirkancorp.user.ui.theme.Typography

private val DarkColorScheme = darkColors(
    primary = theme_primary,
    secondary = theme_secondary,
    background = theme_background_dark
)

private val LightColorScheme = lightColors(
    primary = theme_primary,
    secondary = theme_secondary,
    background = theme_background

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun RuzmozdProviderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalSpacing provides Dimensions(),
        localAlertColors provides AlertColors(),
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        MaterialTheme(
            colors = colorScheme,
            shapes = Shapes,
            typography = Typography,
            content = content
        )
    }

}