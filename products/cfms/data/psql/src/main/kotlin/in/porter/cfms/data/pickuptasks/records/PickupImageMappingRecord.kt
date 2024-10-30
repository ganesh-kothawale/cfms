package `in`.porter.cfms.data.pickuptasks.records

import java.time.Instant

data class PickupImageMappingRecord(
    val taskId: String,
    val pickupDetailsId: String,
    val orderImage: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
