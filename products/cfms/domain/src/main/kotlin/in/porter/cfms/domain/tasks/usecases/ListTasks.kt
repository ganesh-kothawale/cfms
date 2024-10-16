package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasks
@Inject
constructor(
    private val tasksRepo: TasksRepo
) {

    private val logger = LoggerFactory.getLogger(ListTasks::class.java)

    suspend fun listTasks(page: Int, limit: Int): TaskResult {
        logger.info("Listing tasks with page: $page, limit: $limit")

        // Fetch the total number of task records
        val totalRecords = tasksRepo.countAllTasks()

        // Fetch the paginated list of tasks
        val tasks = tasksRepo.findAllTasks(page, limit)

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
    val data: List<ListTasks>,
    val totalRecords: Int
)