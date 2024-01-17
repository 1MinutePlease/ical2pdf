package domain.use_cases

import core.util.Tags
import domain.model.Event
import domain.model.SearchQuery

class GroupEvents {

    operator fun invoke(
        events: List<Event>,
        searchQueries: List<SearchQuery>
    ): Map<String, List<Event>> {
        val filteredEvents = mutableMapOf<String, List<Event>>()

        events.groupBy { event ->
            var added = false
            searchQueries.forEach search@{ query ->
                query.exclude.forEach { excludeString ->
                    if (event.name.contains(excludeString.value, false)) {
                        return@search
                    }
                }
                query.include.forEach { includeString ->
                    if (event.name.contains(includeString.value, false)) {
                        filteredEvents[query.name] = filteredEvents[query.name]?.plus(event) ?: listOf(event)
                        added = true
                        return@search
                    }
                }
            }

            if (!added) {
                filteredEvents[Tags.OTHERS] = filteredEvents[Tags.OTHERS]?.plus(event) ?: listOf(event)
            }
        }

        return filteredEvents
    }
}