package `in`.porter.cfms.data.auditlogs.repos

import `in`.porter.cfms.data.auditlogs.AuditLogsQueries
import `in`.porter.cfms.data.auditlogs.mappers.AuditLogMapper
import `in`.porter.cfms.data.auditlogs.records.AuditLogRecord
import `in`.porter.cfms.domain.auditlogs.entities.AuditLog
import `in`.porter.cfms.domain.auditlogs.repos.AuditLogRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlAuditLogsRepo @Inject constructor(
    private val queries: AuditLogsQueries,
    private val auditLogMapper: AuditLogMapper
) : Traceable, AuditLogRepo {

    private val logger = LoggerFactory.getLogger(PsqlAuditLogsRepo::class.java)

    // Method for creating an audit log
    override suspend fun create(auditLog: AuditLog) =
        trace("createAuditLog") {
            try {
                logger.info("Creating a new audit log in the database")
                auditLogMapper.toRecord(auditLog)
                    .let{queries.insert(it)}
                logger.info("Audit log created successfully:")
            } catch (e: Exception) {
                logger.error("Error occurred while creating an audit log: ${e.message}", e)
                throw Exception("Failed to create audit log: ${e.message}")
            }
        }
}
