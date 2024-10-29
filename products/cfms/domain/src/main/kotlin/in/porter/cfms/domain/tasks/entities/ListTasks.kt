package `in`.porter.cfms.domain.tasks.entities

import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.cfms.domain.recon.entities.Recon
import java.time.Instant

data class ListTasks(
    val taskId: String,
    val flowType: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val pickupTaskEntity: PickupTask? = null,   // Optional field for pickup task data
    val reconEntity: Recon? = null
)
