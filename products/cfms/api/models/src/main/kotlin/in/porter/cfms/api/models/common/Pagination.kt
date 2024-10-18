package `in`.porter.cfms.api.models.common

data class Pagination(
    val page: Int,
    val size: Int,
    val totalPages: Int
)
