package domain.model

import java.time.LocalDateTime

data class Event(
    val name: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
)
