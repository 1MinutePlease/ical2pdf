package presentation.choose_file

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun ChooseFileScreen(
    modifier: Modifier = Modifier,
    onFileSelected: (String) -> Unit,
    calenderFile: File?,
    navigateForward: () -> Unit
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
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = navigateForward,
                enabled = calenderFile != null,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text("Next")
            }
        }
    }
}