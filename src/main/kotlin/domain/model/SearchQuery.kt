package domain.model

data class SearchQuery(
    val id: Int,
    val name: String,
    val include: Map<Int, String>,
    val exclude: Map<Int, String>,
)
