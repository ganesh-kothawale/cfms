package `in`.porter.cfms.domain.pickuptasks.entities

data class PickupOrder(
    val orderId: String,
    val awbNumber: String,
    val senderName: String?,
    val receiverName: String?,
    val status: String,
    val crNumber: String?
)
