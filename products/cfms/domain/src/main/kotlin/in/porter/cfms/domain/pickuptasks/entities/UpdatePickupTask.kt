package `in`.porter.cfms.domain.pickuptasks.entities


import java.util.UUID

data class UpdatePickupTask(
    val taskId: String,
    val noOfPackagesReceived: Int?,
    val orders: List<Order>,
    val orderImages: List<UUID>
)

data class Order(
    val orderId: String,
    val status: String?
)
