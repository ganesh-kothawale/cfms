package `in`.porter.cfms.domain.orders.entities

import `in`.porter.cfms.domain.common.entities.Pagination

data class FetchOrdersResponse(
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalRecords: Int,
    val orders: List<Order>
)

