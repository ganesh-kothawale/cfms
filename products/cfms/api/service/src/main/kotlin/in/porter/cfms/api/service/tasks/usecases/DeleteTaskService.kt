package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.domain.tasks.usecases.DeleteTask
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteTaskService @Inject constructor(
    private val deleteTask: DeleteTask
) {

    private val logger = LoggerFactory.getLogger(DeleteTaskService::class.java)

    suspend fun deleteTask(taskId: String) {
        try {
            logger.info("Request received to delete task with ID: $taskId")

            // Forward the request to the domain use case
            deleteTask.delete(taskId)

            logger.info("Task with ID $taskId deleted successfully")

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
