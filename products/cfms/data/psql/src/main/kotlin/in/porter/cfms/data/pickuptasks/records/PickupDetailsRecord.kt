package `in`.porter.cfms.data.pickuptasks.records

import java.time.Instant
import java.util.*

data class PickupDetailsRecord(
    val pickupDetailsId: String,
    val taskId: String,
    val hlpId: String,
    val franchiseId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val orderImages: List<UUID>?,
    val packageReceived: Int?
)
