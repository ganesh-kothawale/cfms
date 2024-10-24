package `in`.porter.cfms.domain.pickuptasks.entities

import java.time.Instant

data class PickupImageMapping(
    val taskId: String,
    val pickupDetailsId: String,
    val orderImage: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
