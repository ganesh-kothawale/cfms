package `in`.porter.cfms.api.service.pickupTasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.pickupTasks.UpdatePickupTaskRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.pickupTasks.mappers.UpdatePickupTaskRequestMapper
import `in`.porter.cfms.domain.pickuptasks.usecases.internal.UpdatePickupTask
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdatePickupTaskService @Inject constructor(
    private val updatePickupTask: UpdatePickupTask, // Domain layer use case
    private val updatePickupTaskRequestMapper: UpdatePickupTaskRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(UpdatePickupTaskService::class.java)
    suspend fun invoke(request: UpdatePickupTaskRequest) {
        try {
            logger.info("Received request to update pickup task: ${request.taskId}")
            val domainPickupTask = updatePickupTaskRequestMapper.toDomain(request)
            updatePickupTask.updatePickupDetails(domainPickupTask)

            updatePickupTask.updateOrderStatuses(domainPickupTask)

            logger.info("Pickup task updated successfully for ID: ${request.taskId}")

        } catch (e: CfmsException) {
            logger.error("Validation error: ${e.message}")
            throw e
        } catch (ne: NoSuchElementException) {
            logger.error("Not Found: ${ne.message}")
            throw ne

        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred while updating the pickup task.")
        }
    }
}
