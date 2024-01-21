package presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    enabledForward: Boolean,
    forwardTitle: String = "Next",
    navigateForward: (() -> Unit)?,
    enabledBack: Boolean = true,
    navigateBack: (() -> Unit)?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        navigateBack?.let {
            OutlinedButton(
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
                Text(forwardTitle)
            }
        }
    }
}