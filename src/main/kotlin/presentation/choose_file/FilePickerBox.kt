package presentation.choose_file

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

@Composable
fun FilePickerBox(
    modifier: Modifier = Modifier,
    onFileSelected: (String) -> Unit
) {
    var pickerShown by remember { mutableStateOf(false) }
    FilePicker(
        show = pickerShown,
        fileExtensions = listOf("ics"),
        title = "Choose calendar file"
    ) {
        pickerShown = false
        it?.let { it1 -> onFileSelected(it1.path) }
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .clickable { pickerShown = true }
            .background(MaterialTheme.colorScheme.secondary)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Click to open file")
    }
}