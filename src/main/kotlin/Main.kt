import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.repository.ICSRepositoryImpl
import domain.model.SearchQuery
import domain.use_cases.FilterEvents
import domain.use_cases.GetEvents
import java.time.LocalDateTime

@Composable
@Preview
fun App() {
    val getEvents = GetEvents(ICSRepositoryImpl())
    val filterEvents = FilterEvents()
    val searchQueries = listOf(
        SearchQuery(
            name = "JC",
            include = listOf("Jugendcafé", "Jugendcafe", "JCx"),
            exclude = listOf("extra Jugendcafé", "extra Jugendcafe", "eJC")
        ),
        SearchQuery(
            name = "eJC",
            include = listOf("extra Jugendcafé", "extra Jugendcafe", "eJC"),
            exclude = listOf(),
        )
    )

    val events = getEvents(
        path = "src/main/resources/kjg_st_martin.ics",
        from = LocalDateTime.of(2024, 1, 1, 0, 0),
        to = LocalDateTime.of(2025, 1, 7, 0, 0)
    )
    val filteredEvents = filterEvents(
        events = events,
        searchQueries = searchQueries
    )

    MaterialTheme {
        Text(filteredEvents.map { (label, entries) -> label to  entries.map { it.name } }.toString())
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
