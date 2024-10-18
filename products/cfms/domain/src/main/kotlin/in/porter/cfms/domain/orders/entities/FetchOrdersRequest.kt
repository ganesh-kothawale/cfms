package `in`.porter.cfms.domain.orders.entities

data class FetchOrdersRequest(
    val page: Int,
    val size: Int,
    val franchiseId: String?,
)
