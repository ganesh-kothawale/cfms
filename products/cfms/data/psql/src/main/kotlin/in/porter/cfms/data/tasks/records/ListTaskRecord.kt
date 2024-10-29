package `in`.porter.cfms.data.tasks.records

import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.cfms.domain.recon.entities.Recon
import java.time.Instant

data class ListTaskRecord(
    val taskId: String,
    val flowType: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val pickupTaskEntity: PickupTask? = null,  // Nullable, to be present only if flowType is "Pickup"
    val reconEntity: Recon? = null             // Nullable, to be present if flowType is "Recon" or other
)
