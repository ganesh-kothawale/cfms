package `in`.porter.cfms.data.pickuptasks.records

import java.time.Instant

data class PickupDetailsRecord(
    val pickupDetailsId: String,
    val taskId: String,
    val hlpId: String,
    val franchiseId: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
