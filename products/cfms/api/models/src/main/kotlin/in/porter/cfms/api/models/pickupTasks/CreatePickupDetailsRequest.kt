package `in`.porter.cfms.api.models.pickupTasks

import java.util.*

data class CreatePickupDetailsRequest(
    val taskId: String,
    val orderId: String,
    val hlpId: String,
    val franchiseId: String,
    val status: String,
    val orderImages: List<UUID>,
    val packageReceived: Int?
)