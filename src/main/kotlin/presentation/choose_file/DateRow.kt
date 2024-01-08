package presentation.choose_file

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateRow(
    modifier: Modifier = Modifier,
    title: String,
    date: LocalDateTime?,
    onClickDate: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = date?.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")) ?: "Not selected",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = onClickDate)
        )
    }
}