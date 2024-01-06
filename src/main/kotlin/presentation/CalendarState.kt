package presentation

import domain.model.Event
import java.io.File
import java.time.LocalDateTime

data class CalendarState(
    val sourceFile: File? = null,
    val from: LocalDateTime? = LocalDateTime.of(2023,1,1,0,0), // null,
    val to: LocalDateTime? = LocalDateTime.of(2025,1,7,0,0), // null,
    val events: List<Event> = emptyList(),
    val chosenEventIds: List<Int> = emptyList()
)
