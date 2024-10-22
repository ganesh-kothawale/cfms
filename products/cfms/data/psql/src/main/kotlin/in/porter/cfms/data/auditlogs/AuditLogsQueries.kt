package `in`.porter.cfms.data.auditlogs

import `in`.porter.cfms.data.auditlogs.mappers.AuditLogRowMapper
import `in`.porter.cfms.data.auditlogs.records.AuditLogRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory
import java.time.Instant
import javax.inject.Inject

class AuditLogsQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val auditLogRowMapper: AuditLogRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(AuditLogsQueries::class.java)

    // Insert a new audit log into the database
    suspend fun insert(auditLogRecord: AuditLogRecord): String = transact {
        logger.info("Inserting a new audit log into the database")

        AuditLogsTable.insert { row ->
            row[auditLogId] = auditLogRecord.auditLogId
            row[entityId] = auditLogRecord.entityId
            row[entityType] = auditLogRecord.entityType
            row[status] = auditLogRecord.status
            row[message] = auditLogRecord.message
            row[updatedBy] = auditLogRecord.updatedBy
            row[changeTimestamp] = auditLogRecord.changeTimestamp
            row[createdAt] = auditLogRecord.createdAt ?: Instant.now()
            row[updatedAt] = auditLogRecord.updatedAt ?: Instant.now()
        }
        auditLogRecord.auditLogId
    }
}
