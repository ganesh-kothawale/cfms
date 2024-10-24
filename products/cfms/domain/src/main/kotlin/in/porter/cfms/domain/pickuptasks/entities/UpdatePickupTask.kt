package `in`.porter.cfms.domain.pickuptasks.entities

data class UpdatePickupTask(
    val taskId: String,
    val noOfPackagesReceived: Int?,
    val taskStatus: String,
    val orders: List<Order>,
    val orderImage: String
)

data class Order(
    val orderId: String,
    val status: String
)
