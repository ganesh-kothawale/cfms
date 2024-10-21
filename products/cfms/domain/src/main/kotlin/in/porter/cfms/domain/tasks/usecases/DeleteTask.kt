package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteTask @Inject constructor(
    private val tasksRepo: TasksRepo
) {

    private val logger = LoggerFactory.getLogger(DeleteTask::class.java)

    suspend fun delete(taskId: String) {
        logger.info("Attempting to delete task with ID: $taskId")

        // Find the task by its ID
        tasksRepo.findTaskById(taskId)
            ?: throw CfmsException("Task with ID $taskId not found")

        // Delete the task using the repository
        tasksRepo.deleteTaskById(taskId)

        logger.info("Task with ID $taskId successfully deleted")
    }
}
