package `in`.porter.cfms.domain.orders.entities

import `in`.porter.cfms.domain.common.entities.Pagination

data class FetchOrdersResponse(
    val pagination: Pagination,
    val orders: List<Order>
)

