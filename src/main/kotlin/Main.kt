import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
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
    val state by calendarViewModel.state.collectAsState()

    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        Surface {
            val navigator = rememberNavigator()
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = Screen.ChooseFile.route
            ) {
                scene(Screen.ChooseFile.route) {
                    ChooseFileScreen(
                        onFileSelected = {
                            calendarViewModel.selectSourceFile(it)
                        },
                        calenderFile = state.sourceFile,
                        navigateForward = {
                            calendarViewModel.loadEventsFromSourceFile()
                            navigator.navigate(Screen.ChooseEvents.route)
                        },
                        onConfirmDates = { from, to ->
                            from?.let { it1 -> calendarViewModel.setFrom(it1) }
                            to?.let { it1 -> calendarViewModel.setTo(it1) }
                        }
                    )
                }
                scene(Screen.ChooseEvents.route) {
                    ChooseEventsScreen(
                        navigateForward = { navigator.navigate(Screen.GroupEvents.route) },
                        navigateBack = { navigator.popBackStack() },
                        onCheckedChangeEvent = calendarViewModel::checkedChangeEvent,
                        events = state.events,
                        chosenEvents = state.chosenEventIds
                    )
                }
                scene(Screen.GroupEvents.route) {
                    GroupEventsScreen(
                        navigateForward = { navigator.navigate(Screen.GeneratePdf.route) },
                        navigateBack = { navigator.popBackStack() }
                    )
                }
                scene(Screen.GeneratePdf.route) {
                    GeneratePdfScreen(
                        navigateBack = { navigator.popBackStack() }
                    )
                }
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
            onCloseRequest = ::exitApplication,
            icon = painterResource("icon.ico")
        ) {
            PreComposeApp {
                KoinContext {
                    App()
                }
            }
        }
    }
}
