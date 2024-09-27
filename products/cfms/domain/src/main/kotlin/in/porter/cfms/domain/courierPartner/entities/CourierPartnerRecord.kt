package `in`.porter.cfms.domain.courierPartner.entities

import java.time.Instant

data class CourierPartnerRecord(
    val createdAt: Instant,
    val courierPartnerId: Int,
    val franchiseId: Int,
    val manifestImageUrl: String?,
)
