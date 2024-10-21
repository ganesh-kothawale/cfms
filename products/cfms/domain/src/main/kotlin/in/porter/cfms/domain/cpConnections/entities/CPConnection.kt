package `in`.porter.cfms.domain.cpConnections.entities

import java.time.Instant

data class CPConnection(
    val id: Int,
    val cpId: Int,
    val franchiseId: String,
    val manifestImageUrl: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
