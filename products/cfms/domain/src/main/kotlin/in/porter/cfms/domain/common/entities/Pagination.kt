package `in`.porter.cfms.domain.common.entities

data class Pagination(
    val page: Int,
    val size: Int,
    val totalPages: Int
)
