package `in`.porter.cfms.domain.orders.entities

import `in`.porter.cfms.domain.common.entities.paginationToken

data class FetchOrdersResponse(
    val pagination: paginationToken,
    val orders: List<Order>
)

