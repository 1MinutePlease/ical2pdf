package presentation.common

import core.util.generateUniqueId
import core.util.toLocalDateTime
import domain.model.SearchQuery
import domain.use_cases.GetEvents
import domain.use_cases.GroupEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import java.io.File

class CalendarViewModel(
    private val getEvents: GetEvents,
    private val groupEvents: GroupEvents
): ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state = _state.asStateFlow()

    fun selectSourceFile(path: String) {
        _state.update {
            it.copy(
                sourceFile = File(path).run { if (!isFile) null else this }
            )
        }
    }

    fun setFrom(fromMillis: Long) {
        _state.update {
            it.copy(
                from = fromMillis.toLocalDateTime()
            )
        }
    }

    fun setTo(toMillis: Long) {
        _state.update {
            it.copy(
                to = toMillis.toLocalDateTime()
            )
        }
    }

    fun loadEventsFromSourceFile() {
        if (state.value.from == null) return
        if (state.value.to == null) return
        if (state.value.sourceFile == null) return

        print("from: ${state.value.from}, to: ${state.value.to}")

        _state.update {
            val events = getEvents(
                path = state.value.sourceFile!!.absolutePath,
                from = state.value.from!!,
                to = state.value.to!!
            )
            it.copy(
                events = events,
                chosenEventIds = events.map { event -> event.id }
            )
        }
    }

    fun checkedChangeEvent(id: Int, selected: Boolean) {
        _state.update {
            it.copy(
                chosenEventIds = it.chosenEventIds.let { list ->
                    if (selected) list.plus(id)
                    else list.minus(id)
                }
            )
        }
    }

    fun addSearchQuery(
        name: String,
        acronym: String,
        include: Map<Int, String>,
        exclude: Map<Int, String>
    ) {
        _state.update { it ->
            it.copy(
                searchQueries = it.searchQueries.plus(SearchQuery(
                    id = it.searchQueries.map { query -> query.id }.generateUniqueId(),
                    name = name,
                    acronym = acronym,
                    include = include,
                    exclude = exclude
                ))
            )
        }
    }

    fun updateSearchQuery(searchQuery: SearchQuery) {
        _state.update {
            it.copy(
                searchQueries = it.searchQueries.map { query ->
                    if (query.id != searchQuery.id) query
                    else searchQuery
                }
            )
        }
    }

    fun search() {
        _state.update {
            it.copy(
                groupedEvents = groupEvents(
                    events = state.value.events,
                    searchQueries = state.value.searchQueries
                ).also { print(it) }
            )
        }
    }
}