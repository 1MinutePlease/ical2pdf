package presentation.common

import domain.model.Event
import domain.model.EventGroup
import domain.model.SearchQuery
import java.io.File
import java.time.LocalDateTime

data class CalendarState(
    val sourceFile: File? = File("C:\\Users\\Paul\\Downloads\\kjg_st_martin.ics"),
    val from: LocalDateTime? = LocalDateTime.of(2024, 1, 1, 0, 0),
    val to: LocalDateTime? = LocalDateTime.of(2025, 1, 7, 0, 0),
    val events: List<Event> = emptyList(),
    val chosenEventIds: List<Int> = emptyList(),
    val searchQueries: List<SearchQuery> = listOf(
        SearchQuery(1, "Jugendcafé", "JC", mapOf(1 to "Jugendcafé", 2 to "JC", 3 to "Jugendcafe"), mapOf(1 to "extra Jugendcafé", 2 to "eJC", 3 to "extra Jugendcafe")),
        SearchQuery(2, "Extra Jugendcafé", "eJC", mapOf(1 to "extra Jugendcafé", 2 to "eJC", 3 to "extra Jugendcafe"), emptyMap()),
        SearchQuery(3, "Lager", "Lager", mapOf(1 to "Lager"), emptyMap()),
        SearchQuery(4, "StSi", "Sternsinger", mapOf(1 to "Sternsinger"), emptyMap()),
    ),
    val eventGroups: List<EventGroup> = emptyList()
)
