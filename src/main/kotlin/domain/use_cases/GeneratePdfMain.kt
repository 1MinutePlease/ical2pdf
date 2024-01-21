package domain.use_cases

import androidx.compose.ui.graphics.Color
import core.util.toRGBColor
import domain.model.Event
import domain.model.EventGroup
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

fun main() {
    val jcEvents = listOf(2, 5, 9).map { month ->
        Event(
            id = month,
            name = "JC $month",
            startDateTime = LocalDateTime.of(2024, month, 1, 18, 30),
            endDateTime = LocalDateTime.of(2024, month, 1, 21, 0),
        )
    }
    val fRunden = (1..12).map { month ->
        val dateTime = LocalDateTime.of(2024, month, 1, 19, 30)
            .with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.FRIDAY))
        Event(
            id = month*10,
            name = "F-Runde $month",
            startDateTime = dateTime,
            endDateTime = dateTime.plusHours(3)
        )
    }

    GeneratePdf().invoke(
        title = "Jahresplan KjG St. Martin",
        eventGroups = listOf(
            EventGroup("FR", "F-Runde", fRunden, Color.Blue.toRGBColor()),
            EventGroup("JC", "Jugendcafe", jcEvents, Color.Green.toRGBColor())
        )
    ).save("C:\\Users\\Paul\\Downloads\\document.pdf")
}