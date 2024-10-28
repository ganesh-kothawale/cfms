package `in`.porter.cfms.api.models.pickupTasks


import java.util.UUID

data class UpdatePickupTaskRequest(
    val taskId: String,
    val noOfPackagesReceived: Int,
    val orderImages: List<UUID>,
    val orders: List<Order>
)

data class Order(
    val orderId: String,
    val status: String
)
