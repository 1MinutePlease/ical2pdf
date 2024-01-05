package domain.repository

import net.fortuna.ical4j.model.Component

interface ICSRepository {

    fun getComponentsFromICS(path: String): List<Component>
}