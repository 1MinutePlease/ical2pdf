import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
import presentation.choose_events.ChooseEventsScreen
import presentation.choose_file.ChooseFileScreen
import presentation.common.CalendarViewModel
import presentation.generate_pdf.GeneratePdfScreen
import presentation.group_events.GroupEventsScreen
import presentation.navigation.Screen

@Composable
@Preview
fun App() {
    val calendarViewModel = koinViewModel<CalendarViewModel>()
    val state by calendarViewModel.state.collectAsState()

    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
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
                        navigateForward = {
                            navigator.navigate(Screen.GeneratePdf.route)
                            calendarViewModel.search()
                        },
                        navigateBack = { navigator.popBackStack() },
                        state = state,
                        calendarViewModel = calendarViewModel
                    )
                }
                scene(Screen.GeneratePdf.route) {
                    GeneratePdfScreen(
                        navigateBack = { navigator.popBackStack() },
                        groupedEvents = state.groupedEvents
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
            icon = painterResource("ic_foreground.svg")
        ) {
            PreComposeApp {
                KoinContext {
                    App()
                }
            }
        }
    }
}