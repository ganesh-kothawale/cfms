package `in`.porter.cfms.domain.common.entities

data class Pagination(
    val page: Int,
    val limit: Int,
    val totalPages: Int
)
