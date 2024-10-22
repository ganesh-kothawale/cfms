package `in`.porter.cfms.domain.auditlogs.entities

import java.time.Instant

data class AuditLog(
    val auditLogId: String,
    val entityId: String,
    val entityType: String,
    val status: String,
    val message: String?,
    val updatedBy: Int,
    val changeTimestamp: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)
