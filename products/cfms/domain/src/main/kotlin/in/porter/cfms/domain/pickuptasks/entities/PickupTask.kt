package `in`.porter.cfms.domain.pickuptasks.entities

import `in`.porter.cfms.domain.pickuptasks.TasksStatus
import java.time.Instant

data class PickupTask(
    val taskId: String,
    val orderId: String,
    val hlpId: String,
    val franchiseId: String,
    val status: TasksStatus,
    val createdAt: Instant,
    val updatedAt: Instant
)
