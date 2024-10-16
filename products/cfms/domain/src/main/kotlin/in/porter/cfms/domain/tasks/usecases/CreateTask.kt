package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.tasks.entities.Task
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreateTask @Inject constructor(
    private val tasksRepo: TasksRepo
) {

    private val logger = LoggerFactory.getLogger(CreateTask::class.java)

    suspend fun create(task: Task): Int {
        logger.info("Creating a new task: $task")

        // Call the repository to persist the task and get the generated ID
        val taskId = tasksRepo.create(task)

        logger.info("Task created with ID: $taskId")
        return taskId
    }
}
