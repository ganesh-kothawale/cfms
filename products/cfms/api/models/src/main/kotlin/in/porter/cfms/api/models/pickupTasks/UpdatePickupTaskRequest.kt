package `in`.porter.cfms.api.models.pickupTasks


import java.util.UUID

data class UpdatePickupTaskRequest(
    val taskId: String,
    val noOfPackagesReceived: Int,
    val orderImage: List<UUID>,
    val taskStatus: String,
    val orders: List<Order>
)

data class Order(
    val orderId: String,
    val status: String
)
