package `in`.porter.cfms.domain.orders.entities

data class UpdateOrderStatusRequest(
    val orderId: String,
    val status: String

)
