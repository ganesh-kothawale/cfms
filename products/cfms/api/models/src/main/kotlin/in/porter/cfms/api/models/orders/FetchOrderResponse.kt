package `in`.porter.cfms.api.models.orders

import `in`.porter.cfms.api.models.common.Pagination

data class FetchOrderResponse(
    val pagination: Pagination,
    val orders: Array<Order>
)
