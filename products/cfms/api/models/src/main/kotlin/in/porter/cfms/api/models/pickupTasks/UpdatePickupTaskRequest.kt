package `in`.porter.cfms.api.models.pickupTasks

data class UpdatePickupTaskRequest(
    val taskId: String,
    val noOfPackagesReceived: Int,
    val orderImage: String,
    val taskStatus: String,
    val orders: List<Order>
)

data class Order(
    val orderId: String,
    val status: String
)
