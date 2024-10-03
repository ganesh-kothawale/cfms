package `in`.porter.cfms.domain.orders.entities

data class UpdateOrderStatusRequest(
    val orderId: Int,
    val status: String
)
