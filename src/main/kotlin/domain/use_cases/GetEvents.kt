package domain.use_cases

import core.util.PropertyTags
import domain.model.Event
import domain.repository.ICSRepository
import net.fortuna.ical4j.model.property.DtEnd
import net.fortuna.ical4j.model.property.DtStart
import net.fortuna.ical4j.model.property.Summary
import java.time.LocalDateTime
import java.time.OffsetTime
import java.time.ZoneOffset

class GetEvents(private val icsRepository: ICSRepository) {

    operator fun invoke(
        path: String,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<Event> {
        return icsRepository.getComponentsFromICS(path).map { component ->
            component.properties.let { properties ->
                val name = properties.getProperty<Summary>(PropertyTags.SUMMARY).value

                val startDateTime =
                    properties.getProperty<DtStart>(PropertyTags.DATE_TIME_START).date.time.div(1000).toLocalDateTime()
                val endDateTime =
                    properties.getProperty<DtEnd>(PropertyTags.DATE_TIME_END).date.time.div(1000).toLocalDateTime()

                if (from.isAfter(startDateTime) || to.isBefore(endDateTime)) {
                    return@let null
                }

                Event(
                    name = name,
                    startDateTime = startDateTime,
                    endDateTime = endDateTime
                )
            }
        }.filterNotNull()
    }

    private fun Long.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofEpochSecond(
            this,
            0,
            ZoneOffset.of(OffsetTime.now().offset.id)
        )
    }
}