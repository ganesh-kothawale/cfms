package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasks
@Inject
constructor(
    private val tasksRepo: TasksRepo
) {

    private val logger = LoggerFactory.getLogger(Tasks::class.java)

    suspend fun listTasks(page: Int, size: Int): TaskResult {
        logger.info("Listing tasks with page: $page, size: $size")

        // Fetch the total number of task records
        val totalRecords = tasksRepo.countAllTasks()

        // Fetch the paginated list of tasks
        val tasks = tasksRepo.findAllTasks(page, size)

        // Log the result
        logger.info("Fetched ${tasks.size} tasks out of $totalRecords total records.")

        // Return the result with the fetched data and total record count
        return TaskResult(
            data = tasks,
            totalRecords = totalRecords
        )
    }
}

data class TaskResult(
    val data: List<Tasks>,
    val totalRecords: Int
)