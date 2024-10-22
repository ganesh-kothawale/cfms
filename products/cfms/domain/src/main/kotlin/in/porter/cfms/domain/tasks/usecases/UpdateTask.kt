package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import java.time.Instant
import javax.inject.Inject

class UpdateTask @Inject constructor(
    private val tasksRepo: TasksRepo,
    private val getTasks: GetTasks
) {

    private val logger = LoggerFactory.getLogger(UpdateTask::class.java)

    suspend fun update(task: Tasks) {
        logger.info("Received request to update task with ID: ${task.taskId}")

        // Find the existing task
        val existingTask = getTasks.findTaskById(task.taskId)

        // Create an updated task with new values but retaining the createdAt timestamp
        val updatedTask = existingTask.copy(
            flowType = task.flowType,
            status = task.status,
            packageReceived = task.packageReceived,
            scheduledSlot = task.scheduledSlot,
            teamId = task.teamId,
            updatedAt = Instant.now()
        )

        // Update the task in the repository
        tasksRepo.update(updatedTask)

        logger.info("Task with ID: ${task.taskId} updated successfully")
    }
}
