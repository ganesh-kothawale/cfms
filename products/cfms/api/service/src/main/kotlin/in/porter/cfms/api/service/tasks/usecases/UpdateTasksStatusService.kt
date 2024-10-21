package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.tasks.UpdateTaskStatusRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.domain.tasks.usecases.UpdateTasksStatus
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateTasksStatusService @Inject constructor(
    private val updateTasksStatus: UpdateTasksStatus,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(UpdateTasksStatusService::class.java)

    suspend fun updateTasksStatus(request: UpdateTaskStatusRequest): List<String> {
        try {
            logger.info("Request received to update tasks: ${request.taskIds}")

            // Validate the taskIds list
            if (request.taskIds.isEmpty()) {
                throw CfmsException("Task IDs list cannot be empty.")
            }

            // Forward the request to the domain use case
            val updatedTaskIds = updateTasksStatus.updateTasksStatus(request.taskIds, request.status)

            // Create an audit log for each updated task
            updatedTaskIds.forEach { taskId ->
                createAuditLogService.createAuditLog(
                    CreateAuditLogRequest(
                        entityId = taskId,
                        entityType = "Task",
                        status = request.status,  // Use the new status from the request
                        message = "Task status updated to ${request.status}",
                        // TODO: Replace hardcoded user ID with actual user ID once it's available
                        updatedBy = 123  // Hardcoded for now
                    )
                )
            }

            // Return the list of updated task IDs
            return updatedTaskIds

        } catch (e: CfmsException) {
            logger.error("Validation error: ${e.message}")
            throw CfmsException("Task IDs list cannot be empty.")
        } catch (e: NoSuchElementException) {
            logger.error("Task not found: ${e.message}")
            throw CfmsException("One or more tasks could not be found.")
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            if (e.message?.contains("violates check constraint") == true) {
                throw CfmsException("Invalid status value. Allowed values are: Pending, In Progress, Completed, Failed.")
            }
            throw CfmsException("An unexpected error occurred on the server.")
        }
    }
}
