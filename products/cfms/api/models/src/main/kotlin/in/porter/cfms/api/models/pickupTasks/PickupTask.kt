package `in`.porter.cfms.api.models.pickupTasks

import `in`.porter.cfms.api.models.TasksStatus
import java.time.Instant

data class PickupTask (
    val taskId: String,
    val orderId: String,
    val hlpId: String,
    val franchiseId: String,
    val status: TasksStatus,
    val createdAt: Instant,
    val updatedAt: Instant
)
