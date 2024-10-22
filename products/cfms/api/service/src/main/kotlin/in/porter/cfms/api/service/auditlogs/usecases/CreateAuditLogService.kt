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
        val generatedAuditLogId = CommonUtils.generateRandomAlphaNumeric(10)
        val domainAuditLog = createAuditLogRequestMapper.toDomain(request,generatedAuditLogId)
        createAuditLog.create(domainAuditLog)
        logger.info("Audit log created successfully with ID: {}", generatedAuditLogId)
    }
}
