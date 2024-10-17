package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.tasks.UpdateTaskStatusRequest
import `in`.porter.cfms.domain.tasks.usecases.UpdateTasksStatus
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateTasksStatusService @Inject constructor(

    private val updateTasksStatus: UpdateTasksStatus
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
            return updateTasksStatus.updateTasksStatus(request.taskIds, request.status)

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
