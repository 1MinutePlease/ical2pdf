import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.CalendarViewModel
import presentation.choose_file.ChooseFileScreen

@Composable
@Preview
fun App() {
    val calendarViewModel = CalendarViewModel()

    MaterialTheme {
        val calenderFile = calendarViewModel.sourceFile.value
        ChooseFileScreen(
            onFileSelected = { calendarViewModel.selectSourceFile(it) },
            calenderFile = calenderFile
        )
    }
}

fun main() = application {
    Window(
        title = "ical2pdf",
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
