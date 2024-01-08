package presentation.choose_file

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.util.toLocalDateTime
import presentation.common.ButtonBar
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseFileScreen(
    modifier: Modifier = Modifier,
    onFileSelected: (String) -> Unit,
    onConfirmDates: (Long?, Long?) -> Unit,
    calenderFile: File?,
    navigateForward: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilePickerBox(onFileSelected = onFileSelected)
        if (calenderFile == null) {
            Text(
                text = "Please select a file for the calendar's source!",
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "${calenderFile.name}\n" +
                        "from \"${calenderFile.absolutePath}\"",
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.height(50.dp))

        Text(
            text = "Choose Date Range",
            color = MaterialTheme.colorScheme.primary
        )
        val dateState = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Input)
        val pickerIsShown = remember { mutableStateOf(false) }
        val onClickDate = { pickerIsShown.value = true }
        DateRow(
            title = "Start Date",
            date = dateState.selectedStartDateMillis?.toLocalDateTime(),
            onClickDate = onClickDate
        )
        DateRow(
            title = "End Date",
            date = dateState.selectedEndDateMillis?.toLocalDateTime(),
            onClickDate = onClickDate
        )

        if (pickerIsShown.value) {
            DatePickerDialog(
                onDismissRequest = {
                    pickerIsShown.value = false
                    onConfirmDates(
                        dateState.selectedStartDateMillis,
                        dateState.selectedEndDateMillis
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            pickerIsShown.value = false
                            onConfirmDates(
                                dateState.selectedStartDateMillis,
                                dateState.selectedEndDateMillis
                            )
                        },
                        enabled = dateState.selectedStartDateMillis != null && dateState.selectedEndDateMillis != null
                    ) {
                        Text(
                            text = "Confirm",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { pickerIsShown.value = false }) {
                        Text(
                            text = "Dismiss",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            ) {
                DateRangePicker(state = dateState)
            }
        }

        Spacer(Modifier.weight(1f))
        ButtonBar(
            enabledForward = calenderFile != null && dateState.selectedStartDateMillis != null && dateState.selectedEndDateMillis != null,
            navigateForward = navigateForward,
            enabledBack = false,
            navigateBack = null,
        )
    }
}