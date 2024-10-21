package `in`.porter.cfms.api.service.auditlogs.mappers

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.domain.auditlogs.entities.AuditLog
import java.time.Instant
import javax.inject.Inject

class CreateAuditLogRequestMapper @Inject constructor() {

    fun toDomain(request: CreateAuditLogRequest): AuditLog {
        return AuditLog(
            auditLogId = "",
            entityId = request.entityId,
            entityType = request.entityType,
            status = request.status,
            message = request.message,
            updatedBy = request.updatedBy,
            changeTimestamp = Instant.now(),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
