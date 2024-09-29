package `in`.porter.cfms.data.orders.entities

data class Pagination(
    val page: Int,
    val limit: Int,
    val totalPages: Int
)