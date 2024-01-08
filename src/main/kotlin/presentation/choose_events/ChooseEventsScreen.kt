package presentation.choose_events

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import domain.model.Event
import presentation.common.ButtonBar
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ChooseEventsScreen(
    modifier: Modifier = Modifier,
    navigateForward: () -> Unit,
    navigateBack: () -> Unit,
    onCheckedChangeEvent: (Int, Boolean) -> Unit,
    events: List<Event>,
    chosenEvents: List<Int>,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Choose Events",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        LazyColumn(Modifier.weight(1f)) {
            stickyHeader {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Select all",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Checkbox(
                        checked = chosenEvents.containsAll(events.map { it.id }),
                        onCheckedChange = {
                            events.forEach { event -> onCheckedChangeEvent(event.id, it)}
                        }
                    )
                }
            }

            items(
                items = events,
                key = { it.id }
            ) { event ->
                ListItem(
                    icon = {
                        Checkbox(
                            checked = chosenEvents.contains(event.id),
                            onCheckedChange = { onCheckedChangeEvent(event.id, it) }
                        )
                    },
                    text = {
                        Text(
                            text = event.name,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    secondaryText = {
                        val pattern = if (event.startDateTime == event.endDateTime) "dd.MM.yy" else "dd.MM.yy HH:mm"
                        println("name: ${event.name}, start: ${event.startDateTime}, end: ${event.endDateTime}, pattern: $pattern")
                        Text(
                            text = event.startDateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern)),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }

        ButtonBar(
            enabledForward = chosenEvents.isNotEmpty(),
            navigateForward = navigateForward,
            enabledBack = true,
            navigateBack = navigateBack
        )
    }
}