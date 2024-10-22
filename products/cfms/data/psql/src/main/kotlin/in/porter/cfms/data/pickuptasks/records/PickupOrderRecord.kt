package `in`.porter.cfms.data.pickuptasks.records

data class PickupOrderRecord(
    val orderId: String,
    val awbNumber: String,
    val crNumber: String,
    val status: String,
    val senderName: String?,
    val receiverName: String?,
)