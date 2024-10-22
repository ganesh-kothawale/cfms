package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateTasksStatus @Inject constructor(
    private val tasksRepo: TasksRepo  // Interacts with the repository
) {

    private val logger = LoggerFactory.getLogger(UpdateTasksStatus::class.java)

    suspend fun updateTasksStatus(taskIds: List<String>, status: String): List<String> {
        logger.info("Updating statuses for tasks: $taskIds")

        // Fetch tasks by their IDs
        val tasksToUpdate = tasksRepo.findTasksByIds(taskIds)

        // If any task is not found, throw an exception
        if (tasksToUpdate.size != taskIds.size) {
            throw NoSuchElementException("One or more tasks not found")
        }

        // Update the status for each task
        tasksRepo.updateStatusForTasks(taskIds, status)

        // Return the updated task IDs as confirmation
        logger.info("Updated statuses for tasks: $taskIds")
        return taskIds
    }
}
