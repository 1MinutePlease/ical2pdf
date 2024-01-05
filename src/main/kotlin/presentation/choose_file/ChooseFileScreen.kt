package presentation.choose_file

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import java.io.File

@Composable
fun ChooseFileScreen(
    modifier: Modifier = Modifier,
    onFileSelected: (String) -> Unit,
    calenderFile: File?
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
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "${calenderFile.name}\n" +
                        "from \"${calenderFile.absolutePath}\""
            )
        }
    }
}