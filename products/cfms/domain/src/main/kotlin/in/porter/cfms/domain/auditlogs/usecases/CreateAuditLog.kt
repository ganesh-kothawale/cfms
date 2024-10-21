package `in`.porter.cfms.domain.auditlogs.usecases

import `in`.porter.cfms.domain.auditlogs.entities.AuditLog
import `in`.porter.cfms.domain.auditlogs.repos.AuditLogRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreateAuditLog @Inject constructor(
    private val auditLogRepo: AuditLogRepo
) {

    private val logger = LoggerFactory.getLogger(CreateAuditLog::class.java)

    suspend fun create(auditLog: AuditLog) {
        logger.info("Creating a new audit log: $auditLog")

        // Call the repository to persist the audit log
        auditLogRepo.create(auditLog)

        logger.info("Audit log created successfully")
    }
}
