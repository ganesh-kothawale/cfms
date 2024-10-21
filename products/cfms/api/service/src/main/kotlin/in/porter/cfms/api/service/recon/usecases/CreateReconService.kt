package `in`.porter.cfms.api.service.recon.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.recon.CreateReconRequest
import `in`.porter.cfms.api.models.recon.CreateReconResponse
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.recon.mappers.CreateReconRequestMapper
import `in`.porter.cfms.api.service.recon.mappers.CreateReconResponseMapper
import `in`.porter.cfms.domain.recon.usecases.CreateRecon
import `in`.porter.cfms.api.service.utils.CommonUtils
import javax.inject.Inject
import org.slf4j.LoggerFactory

class CreateReconService @Inject constructor(
    private val createRecon: CreateRecon,
    private val createReconRequestMapper: CreateReconRequestMapper,
    private val createReconResponseMapper: CreateReconResponseMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(CreateReconService::class.java)

    suspend fun createRecon(request: CreateReconRequest): CreateReconResponse {
        logger.info("Received request to create recon: {}", request)
        val generatedReconId = CommonUtils.generateRandomAlphaNumeric(10)
        val domainRecon = createReconRequestMapper.toDomain(request, generatedReconId)
        val reconId = createRecon.create(domainRecon)
        logger.info("Recon created successfully with ID: {}", reconId)

        // Create audit log after the recon is successfully created
        createAuditLogService.createAuditLog(
            CreateAuditLogRequest(
                entityId = reconId,
                entityType = "Recon",
                status = "Created",
                message = "Recon created successfully",
                //TODO : currently passing hardcoded user ID once UserID development is done need to replace with actual user ID value
                updatedBy = 123
            )
        )
        return createReconResponseMapper.toResponse(reconId, "Recon created successfully")
    }
}
