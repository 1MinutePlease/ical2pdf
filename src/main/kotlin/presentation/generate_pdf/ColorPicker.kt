package presentation.generate_pdf

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    color: Color,
    onColorChange: (Color) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    ColorBox(
        color = color,
        modifier = modifier
            .clickable { isDialogOpen = !isDialogOpen }
    )

    if (isDialogOpen) {
        Dialog(
            onDismissRequest = { isDialogOpen = false },
        ) {
            var pickedColor by remember { mutableStateOf(color) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Pick a color",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.headlineSmall
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(128.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val colors = listOf(
                        Color.White,
                        Color.Black,
                        Color.Red,
                        Color.Blue,
                        Color.Yellow,
                        Color.Green,
                        Color.Gray,
                        Color.Cyan,
                        Color.Magenta,
                    )
                    items(colors) { dialogColor ->
                        ColorBox(
                            color = dialogColor,
                            modifier = Modifier
                                .clickable {
                                    pickedColor = dialogColor }
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    ColorBox(color = pickedColor)
                    Spacer(Modifier.width(16.dp))

                    var textColor by remember(pickedColor) { mutableStateOf(pickedColor.toArgb().toHexString()) }
                    var supportingText by remember { mutableStateOf<String?>(null) }

                    TextField(
                        value = textColor,
                        onValueChange = { textColor = it },
                        maxLines = 1,
                        prefix = { Text("#") },
                        supportingText = supportingText?.let { { Text(it) } },
                        isError = supportingText != null,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .onKeyEvent {
                                if (it.key == Key.Enter) {
                                    try {
                                        pickedColor = Color(textColor.hexToInt())
                                        supportingText = null
                                    } catch (e: NumberFormatException) {
                                        supportingText = "Invalid HEX value"
                                        return@onKeyEvent false
                                    }
                                }
                                true
                            }
                            .clip(CircleShape)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { isDialogOpen = false }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        onColorChange(pickedColor)
                        isDialogOpen = false
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .border(2.dp, Color.White, CircleShape)
            .clip(CircleShape)
            .background(color)
            .then(modifier)
    )
}