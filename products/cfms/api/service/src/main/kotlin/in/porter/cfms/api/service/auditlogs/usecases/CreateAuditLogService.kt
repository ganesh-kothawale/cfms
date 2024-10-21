package `in`.porter.cfms.api.service.auditlogs.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.service.auditlogs.mappers.CreateAuditLogRequestMapper
import `in`.porter.cfms.api.service.utils.CommonUtils
import `in`.porter.cfms.domain.auditlogs.usecases.CreateAuditLog
import javax.inject.Inject
import org.slf4j.LoggerFactory

class CreateAuditLogService @Inject constructor(
    private val createAuditLog: CreateAuditLog,
    private val createAuditLogRequestMapper: CreateAuditLogRequestMapper
) {

    private val logger = LoggerFactory.getLogger(CreateAuditLogService::class.java)

    suspend fun createAuditLog(request: CreateAuditLogRequest) {
        logger.info("Received request to create an audit log: {}", request)

        // Map the request to the domain entity
        val domainAuditLog = createAuditLogRequestMapper.toDomain(request)

        // Generate a new audit log ID
        val generatedAuditLogId = CommonUtils.generateRandomAlphaNumeric(10)
        logger.info("Generated audit log ID: {}", generatedAuditLogId)

        // Create a new instance of domainAuditLog with the generated audit log ID
        val auditLogWithId = domainAuditLog.copy(auditLogId = generatedAuditLogId)

        // Call the domain layer to create the audit log
        createAuditLog.create(auditLogWithId)

        logger.info("Audit log created successfully with ID: {}", generatedAuditLogId)
    }
}
