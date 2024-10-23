package `in`.porter.cfms.api.service.pickupTasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.pickupTasks.CreatePickupDetailsRequest
import `in`.porter.cfms.api.models.pickupTasks.CreatePickupDetailsResponse
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.pickupTasks.mappers.CreatePickupDetailsRequestMapper
import `in`.porter.cfms.api.service.utils.CommonUtils
import `in`.porter.cfms.domain.pickuptasks.usecases.internal.CreatePickupDetails
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreatePickupDetailsService @Inject constructor(
    private val createPickupDetails: CreatePickupDetails,  // Domain use case for creating pickup task
    private val createPickupTaskRequestMapper: CreatePickupDetailsRequestMapper,  // Mapper for request to domain
    private val createAuditLogService: CreateAuditLogService  // Audit log service
) {

    private val logger = LoggerFactory.getLogger(CreatePickupDetailsService::class.java)

    suspend fun invoke(request: CreatePickupDetailsRequest): CreatePickupDetailsResponse {
        logger.info("Received request to create a pickup task: {}", request)

        // Generate the pickupDetailsId
        val generatedPickupDetailsId = CommonUtils.generateRandomAlphaNumeric(10)

        // Map the request to the domain entity
        val domainPickupTask = createPickupTaskRequestMapper.toDomain(request, generatedPickupDetailsId)

        // Create the pickup task in the domain layer
        val pickupDetailsId = createPickupDetails.invoke(domainPickupTask)
        logger.info("Pickup task created successfully with ID: {}", pickupDetailsId)

        // Create an audit log entry
        createAuditLogService.createAuditLog(
            CreateAuditLogRequest(
                entityId = pickupDetailsId,
                entityType = "PickupTask",
                status = "Created",
                message = "Pickup task created successfully",
                // TODO: Replace with actual user ID once user development is done
                updatedBy = 123
            )
        )

        // Return the response
        return CreatePickupDetailsResponse(
            pickupDetailsId = pickupDetailsId,
            message = "Pickup task created successfully"
        )
    }
}
