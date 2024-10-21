package `in`.porter.cfms.domain.hlp.entities

import java.time.Instant

data class HlpDetails(
    val id: Int,
    val hlpOrderId: String,
    val hlpOrderStatus: String?,
    val otp: String?,
    val riderName: String?,
    val riderNumber: String?,
    val vehicleType: String?,
    val franchiseId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
