package `in`.porter.cfms.domain.auditlogs.repos

import `in`.porter.cfms.domain.auditlogs.entities.AuditLog

interface AuditLogRepo {
    suspend fun create(auditLog: AuditLog)
}
