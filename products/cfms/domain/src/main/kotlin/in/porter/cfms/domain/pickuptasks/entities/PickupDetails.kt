package `in`.porter.cfms.domain.pickuptasks.entities

import java.time.Instant

data class PickupDetails(
    val pickupDetailsId: String,
    val taskId: String,
    val hlpId: String,
    val franchiseId: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
