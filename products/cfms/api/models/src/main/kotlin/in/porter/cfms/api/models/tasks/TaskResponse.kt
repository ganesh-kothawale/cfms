package `in`.porter.cfms.api.models.tasks

import `in`.porter.cfms.api.models.pickupTasks.PickupTaskHlpResponse
import `in`.porter.cfms.api.models.recon.ReconResponse

data class TaskResponse(
    val taskId: String,
    val flowType: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val pickupTask: PickupTaskHlpResponse?,
    val recon: ReconResponse?
)
