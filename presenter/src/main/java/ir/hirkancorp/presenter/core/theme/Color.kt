package ir.hirkancorp.presenter.core.theme
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val theme_primary = Color(0xFFFFC500)
val theme_secondary = Color(0xFFFFC500)
val theme_error = Color(0xFFE53935)

val theme_background = Color(0xFFF9F9F9)
val theme_background_dark = Color(0xFF050505)

data class AlertColors(
    val success: Color = Color(0xFF43A047),
    val error: Color = theme_error,
    val warning: Color = theme_primary,
    val normal: Color = Color(0xFF1E88E5)
)

val localAlertColors = compositionLocalOf { AlertColors() }