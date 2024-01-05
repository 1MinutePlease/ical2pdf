package data.repository

import domain.repository.ICSRepository
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Component
import java.io.FileInputStream

class ICSRepositoryImpl : ICSRepository {

    override fun getComponentsFromICS(path: String): List<Component> {
        val fileInputStream = FileInputStream(path)
        val calendar = CalendarBuilder().build(fileInputStream)
        return calendar.components
    }
}