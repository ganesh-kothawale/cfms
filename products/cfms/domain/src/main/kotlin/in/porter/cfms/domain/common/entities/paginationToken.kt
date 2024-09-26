package `in`.porter.cfms.domain.common.entities

data class paginationToken(
    val page: Int,
    val limit: Int,
    val totalPages: Int
)
