package domain.model

data class SearchQuery(
    val name: String,
    val include: List<String>,
    val exclude: List<String>
)
