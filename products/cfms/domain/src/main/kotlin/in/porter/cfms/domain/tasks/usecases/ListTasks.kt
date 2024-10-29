package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.tasks.entities.DomainListTasksRequest
import `in`.porter.cfms.domain.tasks.entities.TaskResult
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

    suspend fun invoke(request: DomainListTasksRequest): TaskResult {
        logger.info("Listing tasks with request: {}", request)

        // Fetch the total number of task records
        val totalRecords = tasksRepo.countAllTasks(request)

        val tasks = tasksRepo.findAllTasks(request)

        // Log the result
        logger.info("Fetched ${tasks.size} tasks out of $totalRecords total records.")

        // Return the result with the fetched data and total record count
        return TaskResult(
            data = tasks,
            totalRecords = totalRecords
        )
    }
}


