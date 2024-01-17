package presentation.common

import domain.model.Event
import domain.model.SearchQuery
import java.io.File
import java.time.LocalDateTime

data class CalendarState(
    val sourceFile: File? = null,
    val from: LocalDateTime? = null,
    val to: LocalDateTime? = null,
    val events: List<Event> = emptyList(),
    val chosenEventIds: List<Int> = emptyList(),
    val searchQueries: List<SearchQuery> = emptyList(),
    val groupedEvents: Map<String, List<Event>> = emptyMap()
)
