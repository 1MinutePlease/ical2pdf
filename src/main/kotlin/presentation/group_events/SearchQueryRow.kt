package presentation.group_events

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import core.util.generateUniqueId
import domain.model.SearchQuery

@Composable
fun SearchQueryRow(
    modifier: Modifier = Modifier,
    query: SearchQuery?,
    onSave: (SearchQuery) -> Boolean,
    shape: RoundedCornerShape
) {
    var editing by remember { mutableStateOf(query == null) }

    var name by remember { mutableStateOf(query?.name ?: "") }
    var include by remember { mutableStateOf(query?.include ?: mapOf()) }
    var exclude by remember { mutableStateOf(query?.exclude ?: mapOf()) }

    val shapeChild = RoundedCornerShape(4.dp)

    Row(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(shapeChild)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            QueryTextField(
                query = name,
                onQueryChange = { name = it },
                enabled = editing,
                unsavedChanges = query?.name != name,
                modifier = Modifier,
            )
        }

        QueryColumn(
            modifier = Modifier
                .clip(shapeChild)
                .weight(1f),
            items = include,
            onItemAdd = { include = include.plus(include.keys.generateUniqueId() to it) },
            onItemDelete = { include = include.minus(it) },
            onItemEdit = { id, value -> include = include.minus(id).plus(id to value)},
            editingEnabled = editing
        )

        QueryColumn(
            modifier = Modifier
                .clip(shapeChild)
                .weight(1f),
            items = exclude,
            onItemAdd = { exclude = exclude.plus(exclude.keys.generateUniqueId() to it) },
            onItemDelete = { exclude = exclude.minus(it) },
            onItemEdit = { id, value -> exclude = exclude.minus(id).plus(id to value)},
            editingEnabled = editing
        )

        AnimatedContent(editing) {
            if (it) {
                IconButton(
                    onClick = {
                        if (!onSave(
                                query?.copy(
                                    name = name,
                                    include = include,
                                    exclude = exclude
                                ) ?: SearchQuery(
                                    id = 0,
                                    name = name,
                                    include = include,
                                    exclude = exclude
                                )
                            )
                        ) {
                            name = query?.name ?: ""
                            include = query?.include ?: mapOf()
                            exclude = query?.exclude ?: mapOf()
                        }
                        editing = false
                    }
                ) {
                    Icon(Icons.Default.Send, null)
                }
            } else {
                IconButton(onClick = { editing = true }) {
                    Icon(Icons.Default.Edit, null)
                }
            }
        }
    }
}