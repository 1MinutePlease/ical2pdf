package presentation.generate_pdf

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.EventGroup
import presentation.common.components.ButtonBar

@Composable
fun GeneratePdfScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onGroupChange: (EventGroup) -> Unit,
    groupedEvents: List<EventGroup>
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Generate PDF")

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = groupedEvents.toList(),
                key = { it.name }
            ) { group ->
                Row {
                    Text(
                        text = "${group.name} (${group.acronym})",
                        modifier = Modifier
                            .weight(0.2f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(0.8f)
                    ) {
                        group.events.forEach { event ->
                            Text(
                                text = event.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                    ColorPicker(
                        color = Color(group.color ?: Color.Transparent.value),
                        onColorChange = { onGroupChange(group.copy(color = it.value)) }
                    )
                }
            }
        }

        ButtonBar(
            enabledForward = false,
            navigateForward = null,
            enabledBack = true,
            navigateBack = navigateBack,
            modifier = Modifier,
        )
    }
}