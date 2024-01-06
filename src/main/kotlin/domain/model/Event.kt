package domain.model

import java.time.LocalDateTime

data class Event(
    val id: Int,
    val name: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)
