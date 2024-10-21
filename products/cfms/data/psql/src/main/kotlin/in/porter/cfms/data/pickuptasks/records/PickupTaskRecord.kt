package `in`.porter.cfms.data.pickuptasks.records

import `in`.porter.cfms.data.pickuptasks.TasksStatus
import java.time.Instant

data class PickupTaskRecord(
    val taskId: String,
    val orderId: String,
    val hlpId: String,
    val franchiseId: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
