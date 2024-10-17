package `in`.porter.cfms.data.courierPartners.records

import java.time.Instant

data class CpConnectionRecord(
    val id: Int,
    val courierPartnerId: Int,
    val franchiseId: Int,
    val manifestImageUrl: String?,
    val courierPartnerName: String?,
    val createdAt: Instant,
    val updatedAt: Instant
)
