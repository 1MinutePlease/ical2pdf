package presentation.group_events

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import presentation.common.CalendarState
import presentation.common.CalendarViewModel
import presentation.common.compontents.ButtonBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupEventsScreen(
    modifier: Modifier = Modifier,
    navigateForward: () -> Unit,
    navigateBack: () -> Unit,
    state: CalendarState,
    calendarViewModel: CalendarViewModel
) {
    var creating by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Group Events")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
                .clip(RoundedCornerShape(30.dp)),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (creating || state.searchQueries.isNotEmpty()) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(10.dp)
                            .animateItemPlacement(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = "Name",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Acronym",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Include",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Exclude",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            items(
                items = state.searchQueries,
                key = { it.id }
            ) { query ->
                SearchQueryRow(
                    query = query,
                    shape = when {
                        state.searchQueries.indexOf(query) == state.searchQueries.size - 1 && !creating -> RoundedCornerShape(
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp,
                            topStart = 2.dp,
                            topEnd = 2.dp
                        )
                        else -> RoundedCornerShape(2.dp)
                    },
                    onSave = {
                        if (it.name.isNotBlank() &&
                            it.include.isNotEmpty()
                        ) {
                            calendarViewModel.updateSearchQuery(it)
                            true
                        } else false
                    },
                    modifier = Modifier
                        .animateItemPlacement()
                )
            }
            item {
                AnimatedContent(creating) {
                    if (creating) {
                        SearchQueryRow(
                            query = null,
                            onSave = {
                                creating = false
                                if (it.name.isNotBlank() &&
                                    it.include.isNotEmpty()
                                ) {
                                    calendarViewModel.addSearchQuery(it.name, it.acronym, it.include, it.exclude)
                                    true
                                } else false
                            },
                            shape = RoundedCornerShape(
                                topStart = 2.dp,
                                topEnd = 2.dp,
                                bottomStart = 30.dp,
                                bottomEnd = 30.dp
                            )
                        )
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth().animateItemPlacement(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = { creating = true }) {
                                Icon(Icons.Rounded.Add, null)
                            }
                        }
                    }
                }
            }
        }
        ButtonBar(
            enabledForward = state.searchQueries.isNotEmpty(),
            navigateForward = navigateForward,
            enabledBack = true,
            navigateBack = navigateBack,
        )
    }
}