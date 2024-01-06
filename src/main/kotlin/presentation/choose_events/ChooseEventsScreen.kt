package presentation.choose_events

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.Event
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChooseEventsScreen(
    modifier: Modifier = Modifier,
    navigateForward: () -> Unit,
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
            textAlign = TextAlign.Center
        )

        LazyColumn(Modifier.weight(1f)) {
            stickyHeader {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Select all")
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
                            text = event.name
                        )
                    },
                    secondaryText = {
                        val pattern = if (event.startDateTime == event.endDateTime) "dd.MM.yy" else "dd.MM.yy HH:mm"
                        println("name: ${event.name}, start: ${event.startDateTime}, end: ${event.endDateTime}, pattern: $pattern")
                        Text(
                            text = event.startDateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern))
                        )
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = navigateForward,
                enabled = chosenEvents.isNotEmpty(),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text("Next")
            }
        }
    }
}