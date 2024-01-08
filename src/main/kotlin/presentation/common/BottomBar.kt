package presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonBar(
    enabledForward: Boolean,
    navigateForward: (() -> Unit)?,
    enabledBack: Boolean = true,
    navigateBack: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        navigateBack?.let {
            Button(
                onClick = it,
                enabled = enabledBack,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text("Back")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        navigateForward?.let {
            Button(
                onClick = it,
                enabled = enabledForward,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text("Next")
            }
        }
    }
}