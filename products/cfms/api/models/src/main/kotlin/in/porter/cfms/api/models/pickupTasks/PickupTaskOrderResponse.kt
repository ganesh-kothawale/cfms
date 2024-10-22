package `in`.porter.cfms.api.models.pickupTasks

data class PickupTaskOrderResponse(
    val orderId: String,
    val awbNmber: String?,
    val senderName: String?,
    val receiverName: String?,
    val status: String?,
    val crNumber: String?
)
