package `in`.porter.cfms.data.auditlogs.mappers

import `in`.porter.cfms.data.auditlogs.records.AuditLogRecord
import `in`.porter.cfms.domain.auditlogs.entities.AuditLog
import java.time.Instant
import javax.inject.Inject

class AuditLogMapper @Inject constructor() {

    // Convert AuditLogRecord to domain AuditLog entity
    fun toDomain(auditLogRecord: AuditLogRecord): AuditLog {
        return AuditLog(
            auditLogId = auditLogRecord.auditLogId,
            entityId = auditLogRecord.entityId,
            entityType = auditLogRecord.entityType,
            status = auditLogRecord.status,
            message = auditLogRecord.message,
            updatedBy = auditLogRecord.updatedBy,
            changeTimestamp = auditLogRecord.changeTimestamp,
            createdAt = auditLogRecord.createdAt ?: Instant.now(),
            updatedAt = auditLogRecord.updatedAt ?: Instant.now()
        )
    }

    // Convert domain AuditLog entity to AuditLogRecord for persistence
    fun toRecord(auditLog: AuditLog): AuditLogRecord {
        return AuditLogRecord(
            auditLogId = auditLog.auditLogId,
            entityId = auditLog.entityId,
            entityType = auditLog.entityType,
            status = auditLog.status,
            message = auditLog.message,
            updatedBy = auditLog.updatedBy,
            changeTimestamp = auditLog.changeTimestamp,
            createdAt = auditLog.createdAt,
            updatedAt = auditLog.updatedAt
        )
    }
}
