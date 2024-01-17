package presentation.generate_pdf

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    color: Color,
    onColorChange: (Color) -> Unit
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .border(2.dp, Color.White, CircleShape)
            .clip(CircleShape)
            .background(color)
    )
}