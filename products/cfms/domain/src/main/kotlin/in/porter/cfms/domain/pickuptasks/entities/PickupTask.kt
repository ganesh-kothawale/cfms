package `in`.porter.cfms.domain.pickuptasks.entities

import `in`.porter.cfms.domain.pickuptasks.TasksStatus

data class PickupTask(
    val taskId: String,
    val status: TasksStatus,
    val hlpId: String,
    val riderName: String?,
    val riderNumber: String?,
    val vehicleType: String?,
    val pickupOrders: List<PickupOrder>
)
