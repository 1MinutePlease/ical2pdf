package domain.model

data class EventGroup(
    val acronym: String,
    val name: String,
    val events: List<Event>,
    val color: Color? = null
)
