package presentation.group_events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.common.ButtonBar

@Composable
fun GroupEventsScreen(
    modifier: Modifier = Modifier,
    navigateForward: () -> Unit,
    navigateBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Group Events")

        Spacer(modifier = Modifier.weight(1f))
        ButtonBar(
            enabledForward = true,
            navigateForward = navigateForward,
            enabledBack = true,
            navigateBack = navigateBack,
        )
    }
}