package `in`.porter.cfms.api.models.pickupTasks

data class CreatePickupDetailsRequest(
    val taskId: String,
    val orderId: String,
    val hlpId: String,
    val franchiseId: String,
    val status: String
)