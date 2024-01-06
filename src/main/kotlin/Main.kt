import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.AppModule
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import presentation.CalendarViewModel
import presentation.choose_events.ChooseEventsScreen
import presentation.choose_file.ChooseFileScreen
import presentation.generate_pdf.GeneratePdfScreen
import presentation.group_events.GroupEventsScreen
import presentation.navigation.Screen

@Composable
@Preview
fun App() {
    val calendarViewModel = koinViewModel<CalendarViewModel>()

    MaterialTheme {
        val navigator = rememberNavigator()
        val calenderFile = calendarViewModel.sourceFile.collectAsState()

        NavHost(
            navigator = navigator,
            navTransition = NavTransition(),
            initialRoute = Screen.ChooseFile.route
        ) {
            scene(Screen.ChooseFile.route) {
                ChooseFileScreen(
                    onFileSelected = { calendarViewModel.selectSourceFile(it) },
                    calenderFile = calenderFile.value,
                    navigateForward = { navigator.navigate(Screen.ChooseEvents.route) }
                )
            }
            scene(Screen.ChooseEvents.route) {
                ChooseEventsScreen()
            }
            scene(Screen.GroupEvents.route) {
                GroupEventsScreen()
            }
            scene(Screen.GeneratePdf.route) {
                GeneratePdfScreen()
            }
        }
    }
}

fun main() {
    startKoin {
        modules(AppModule.appModule)
    }
    application {
        Window(
            title = "ical2pdf",
            onCloseRequest = ::exitApplication
        ) {
            PreComposeApp {
                KoinContext {
                    App()
                }
            }
        }
    }
}
