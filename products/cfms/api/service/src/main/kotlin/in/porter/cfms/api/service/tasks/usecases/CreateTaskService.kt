package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.tasks.CreateTaskRequest
import `in`.porter.cfms.api.models.tasks.CreateTaskResponse
import `in`.porter.cfms.api.service.tasks.mappers.CreateTaskRequestMapper
import `in`.porter.cfms.domain.tasks.usecases.CreateTask
import javax.inject.Inject
import org.slf4j.LoggerFactory

class CreateTaskService @Inject constructor(
    private val createTask: CreateTask,
    private val createTaskRequestMapper: CreateTaskRequestMapper
) {

    private val logger = LoggerFactory.getLogger(CreateTaskService::class.java)

    suspend fun createTask(request: CreateTaskRequest): CreateTaskResponse {
        logger.info("Received request to create a task: {}", request)

        // Map the request to the domain entity
        val domainTask = createTaskRequestMapper.toDomain(request)

        // Call the domain layer to create the task
        val taskId = createTask.create(domainTask)

        logger.info("Task created successfully with ID: {}", taskId)

        // Return the response
        return CreateTaskResponse(
            taskId = taskId,
            message = "Task created successfully"
        )
    }
}
