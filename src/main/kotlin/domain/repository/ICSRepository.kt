package domain.repository

import net.fortuna.ical4j.model.Component
import java.time.LocalDateTime

interface ICSRepository {

    fun getComponentsFromICS(path: String, from: LocalDateTime, to: LocalDateTime): List<Component>
}