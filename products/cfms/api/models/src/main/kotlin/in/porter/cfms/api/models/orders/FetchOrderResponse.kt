package `in`.porter.cfms.api.models.orders

import `in`.porter.cfms.api.models.common.Pagination

data class FetchOrderResponse(
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalRecords: Int,
    val orders: List<Order>
)
