package `in`.porter.cfms.domain.orders.entities

data class FetchOrdersRequest(
    val page: Int,
    val limit: Int,
    val franchiseId: String?,
)
