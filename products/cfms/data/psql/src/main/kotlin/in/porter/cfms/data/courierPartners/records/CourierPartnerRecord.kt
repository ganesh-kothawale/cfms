package `in`.porter.cfms.data.courierPartners.records

import java.time.Instant

data class CourierPartnerRecord(
    val id: Int,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
