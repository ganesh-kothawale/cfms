package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.tasks.UpdateTaskRequest
import `in`.porter.cfms.api.service.tasks.mappers.UpdateTaskRequestMapper
import `in`.porter.cfms.domain.tasks.usecases.UpdateTask
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateTaskService @Inject constructor(
    private val updateTask: UpdateTask, // Domain layer use case
    private val updateTaskRequestMapper: UpdateTaskRequestMapper
) {

    private val logger = LoggerFactory.getLogger(UpdateTaskService::class.java)

    suspend fun updateTask(request: UpdateTaskRequest) {
        try {
            logger.info("Received request to update task: ${request.taskId}")

            // Map the request to the domain entity
            val domainTask = updateTaskRequestMapper.toDomain(request)

            // Call the domain layer to update the task
            updateTask.update(domainTask)

            logger.info("Task updated successfully for ID: ${request.taskId}")
        } catch (e: CfmsException) {
            logger.error("Validation error: ${e.message}")
            throw CfmsException("Task update failed: ${e.message}")
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred while updating the task.")
        }
    }
}
