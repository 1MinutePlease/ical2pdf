package core.util

import domain.model.Color
import kotlin.math.roundToInt

fun Color.toComposeColor(): androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color(red, green, blue)

fun Color.toJavaColor(): java.awt.Color = java.awt.Color(red, green, blue)

fun androidx.compose.ui.graphics.Color.toRGBColor(): Color = Color(
    red.times(255).roundToInt(),
    green.times(255).roundToInt(),
    blue.times(255).roundToInt()
)

fun java.awt.Color.getBestTextColor(): java.awt.Color {
    val brightness = (red * 299 + green * 587 + blue * 114) / 1000
    return if (brightness >= 128) java.awt.Color.BLACK else java.awt.Color.WHITE
}