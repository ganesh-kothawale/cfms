package `in`.porter.cfms.domain.pickuptasks.entities


import java.util.UUID

data class UpdatePickupTask(
    val taskId: String,
    val noOfPackagesReceived: Int?,
    val taskStatus: String,
    val orders: List<Order>,
    val orderImage: List<UUID>
)

data class Order(
    val orderId: String,
    val status: String
)
