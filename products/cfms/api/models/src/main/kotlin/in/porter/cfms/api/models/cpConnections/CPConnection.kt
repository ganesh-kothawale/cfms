package `in`.porter.cfms.api.models.cpConnections

import java.time.Instant

data class CPConnection(
    val id: Int,
    val cpId: Int,
    val franchiseId: String,
    val manifestImageUrl: String?,
    val courierPartnerName: String,
    val createdAt: Instant,
)
