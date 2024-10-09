package `in`.porter.cfms.api.models.common

data class Pagination(
    val page: Int,
    val limit: Int,
    val totalPages: Int
)
