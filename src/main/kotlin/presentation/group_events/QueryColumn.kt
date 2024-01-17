package presentation.group_events

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun QueryColumn(
    modifier: Modifier = Modifier,
    items: Map<Int, String>,
    onItemAdd: (String) -> Unit,
    onItemEdit: (Int, String) -> Unit,
    onItemDelete: (Int) -> Unit,
    editingEnabled: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items.forEach { (id, value) ->
            val interactionSource = MutableInteractionSource()
            val focused by interactionSource.collectIsFocusedAsState()
            QueryTextField(
                query = value,
                trailingIcon = if (!focused) {
                    {}
                } else {
                    {
                        IconButton(onClick = { onItemDelete(id) }) {
                            Icon(Icons.Default.Delete, null)
                        }
                    }
                },
                onQueryChange = { onItemEdit(id, it) },
                interactionSource = interactionSource,
                enabled = editingEnabled,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AnimatedVisibility(editingEnabled) {
            var creating by remember { mutableStateOf(false) }
            AnimatedContent(creating) {
                if (creating) {
                    var text by remember { mutableStateOf("") }
                    QueryTextField(
                        query = text,
                        trailingIcon = {
                            IconButton(onClick = { onItemAdd(text); creating = false }) {
                                Icon(Icons.Rounded.Add, null)
                            }
                        },
                        onQueryChange = { text = it },
                        unsavedChanges = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = { creating = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background.run { copy(alpha = 0.5f) })
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}