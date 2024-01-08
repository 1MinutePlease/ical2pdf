package data.repository

import core.util.FileNames
import core.util.toIcalDateTime
import domain.repository.ICSRepository
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.filter.Filter
import net.fortuna.ical4j.filter.predicate.PeriodRule
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.Period
import java.io.FileInputStream
import java.time.LocalDateTime

class FakeICSRepository: ICSRepository {
    override fun getComponentsFromICS(path: String, from: LocalDateTime, to: LocalDateTime): List<Component> {
        if (path == FileNames.kjgIntern || path == FileNames.kjgStMartin) {
            val fileInputStream = FileInputStream(path)
            val calendar = CalendarBuilder().build(fileInputStream)

            val period = Period(DateTime(from.toIcalDateTime()), DateTime(to.toIcalDateTime()))
            val filter = Filter(PeriodRule(period))

            return filter.filter(calendar.components as Collection<Component>?).toList()
        }

        throw IllegalArgumentException("Path must be one of the following: ${FileNames.kjgIntern}, ${FileNames.kjgStMartin}")
    }
}