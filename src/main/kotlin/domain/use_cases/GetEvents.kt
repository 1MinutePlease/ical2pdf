package domain.use_cases

import core.util.toLocalDateTime
import domain.model.Event
import domain.repository.ICSRepository
import net.fortuna.ical4j.model.Property
import net.fortuna.ical4j.model.property.DtEnd
import net.fortuna.ical4j.model.property.DtStart
import net.fortuna.ical4j.model.property.Summary
import java.time.LocalDateTime

class GetEvents(private val icsRepository: ICSRepository) {

    operator fun invoke(
        path: String,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<Event> {
        val ids = mutableListOf<Int>()
        var highest = 100_000
        for (i in 0..highest) ids.add(i)

        return icsRepository.getComponentsFromICS(
            path = path,
            from = from,
            to = to
        ).map { component ->
            if (ids.isEmpty()) {
                for (i in highest + 1..highest * 2) ids.add(i)
                highest *= 2
            }

            component.properties.let { properties ->
                val startDateTime =
                    properties.getProperty<DtStart>(Property.DTSTART).date.time.toLocalDateTime()
                val endDateTime =
                    properties.getProperty<DtEnd>(Property.DTEND).date.time.toLocalDateTime()
                val name = properties.getProperty<Summary>(Property.SUMMARY).value

                Event(
                    id = ids.random().also { ids.remove(it) },
                    name = name,
                    startDateTime = startDateTime,
                    endDateTime = endDateTime,
                )
            }
        }
    }
}