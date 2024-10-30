package `in`.porter.cfms.api.service.packageIssue.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.packageIssue.UpdatePackageIssueRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.packageIssue.mappers.UpdatePackageIssueRequestMapper
import `in`.porter.cfms.api.service.pickupTasks.usecases.UpdatePickupTaskService
import `in`.porter.cfms.domain.packageIssue.usecases.UpdatePackageIssue
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdatePackageIssueService @Inject constructor(
    private val updatePackageIssue: UpdatePackageIssue, // Domain layer use case
    private val updatePackageIssueRequestMapper: UpdatePackageIssueRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(UpdatePickupTaskService::class.java)
    suspend fun invoke(request: UpdatePackageIssueRequest) {
        try {
            logger.info("Received request to update task action: ${request.taskId}")
            val domainRequest = updatePackageIssueRequestMapper.toDomain(request)

            updatePackageIssue.invoke(domainRequest)

            logger.info("Task action updated successfully for ID: ${request.taskId}")

            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = request.taskId.toString(),
                    entityType = "Task",
                    status = "Updated",
                    message = "Task Action updated to ${request.action} successfully",
                    // TODO: Replace hardcoded user ID with actual user ID once it's available
                    updatedBy = 123
                )
            )

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
