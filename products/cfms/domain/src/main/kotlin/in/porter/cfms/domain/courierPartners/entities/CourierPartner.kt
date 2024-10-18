package `in`.porter.cfms.domain.courierPartners.entities

import java.time.Instant

data class CourierPartner(
    val id: Int,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
