package `in`.porter.cfms.data.auditlogs.mappers

import `in`.porter.cfms.data.auditlogs.AuditLogsTable
import `in`.porter.cfms.data.auditlogs.records.AuditLogRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class AuditLogRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(AuditLogRowMapper::class.java)

    fun toRecord(row: ResultRow): AuditLogRecord {
        logger.info("Mapping result row to AuditLogRecord")
        return AuditLogRecord(
            auditLogId = row[AuditLogsTable.auditLogId],
            entityId = row[AuditLogsTable.entityId],
            entityType = row[AuditLogsTable.entityType],
            status = row[AuditLogsTable.status],
            message = row[AuditLogsTable.message],
            updatedBy = row[AuditLogsTable.updatedBy],
            changeTimestamp = row[AuditLogsTable.changeTimestamp],
            createdAt = row[AuditLogsTable.createdAt],
            updatedAt = row[AuditLogsTable.updatedAt]
        )
    }
}
