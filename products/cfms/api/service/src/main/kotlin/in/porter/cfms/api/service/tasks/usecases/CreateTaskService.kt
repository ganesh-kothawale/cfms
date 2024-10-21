package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.tasks.CreateTaskRequest
import `in`.porter.cfms.api.models.tasks.CreateTaskResponse
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.tasks.mappers.CreateTaskRequestMapper
import `in`.porter.cfms.domain.tasks.usecases.CreateTask
import javax.inject.Inject
import org.slf4j.LoggerFactory

class CreateTaskService @Inject constructor(
    private val createTask: CreateTask,
    private val createTaskRequestMapper: CreateTaskRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(CreateTaskService::class.java)

    suspend fun createTask(request: CreateTaskRequest): CreateTaskResponse {
        logger.info("Received request to create a task: {}", request)

        // Map the request to the domain entity
        val domainTask = createTaskRequestMapper.toDomain(request)

        // Generate a new task ID
        val generatedTaskId = `in`.porter.cfms.api.service.utils.CommonUtils.generateRandomAlphaNumeric(10)
        logger.info("Generated task ID: {}", generatedTaskId)

        // Create a new instance of domainTask with the generated task ID
        val taskWithId = domainTask.copy(taskId = generatedTaskId)

        // Call the domain layer to create the task
        val taskId = createTask.create(taskWithId)

        logger.info("Task created successfully with ID: {}", taskId)

        // Create audit log after the task is successfully created
        createAuditLogService.createAuditLog(
            CreateAuditLogRequest(
                entityId = taskId,
                entityType = "Task",
                status = "Created",
                message = "Task created successfully",
                //TODO : currently passing hardcoded user ID once UserID development is done need to replace with actual user ID value
                updatedBy = 123
            )
        )

        // Return the response
        return CreateTaskResponse(
            taskId = taskId,
            message = "Task created successfully"
        )
    }
}
