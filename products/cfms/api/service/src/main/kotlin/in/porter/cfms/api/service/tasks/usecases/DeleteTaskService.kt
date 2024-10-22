package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.domain.tasks.usecases.DeleteTask
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteTaskService @Inject constructor(
    private val deleteTask: DeleteTask,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(DeleteTaskService::class.java)

    suspend fun deleteTask(taskId: String) {
        try {
            logger.info("Request received to delete task with ID: $taskId")

            // Forward the request to the domain use case
            deleteTask.delete(taskId)

            logger.info("Task with ID $taskId deleted successfully")

            // Create audit log after the task is successfully updated
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = taskId,
                    entityType = "Task",
                    status = "Deleted",
                    message = "Task deleted successfully",
                    //TODO : currently passing hardcoded user ID once UserID development is done need to replace with actual user ID value
                    updatedBy = 123
                )
            )

        } catch (e: CfmsException) {
            logger.error("Task deletion error: ${e.message}")
            throw CfmsException("Failed to delete task.")
        } catch (e: NoSuchElementException) {
            logger.error("Task not found: ${e.message}")
            throw CfmsException("Task with ID $taskId not found.")
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred on the server.")
        }
    }
}
