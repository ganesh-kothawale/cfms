package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.tasks.ListTasksResponse
import `in`.porter.cfms.api.models.tasks.TaskResponse
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import javax.inject.Inject

class ListTasksResponseMapper @Inject constructor() {

    fun toResponse(
        tasks: List<ListTasks>,  // List of ListTasks (domain entity)
        page: Int,
        limit: Int,
        totalPages: Int,
        totalRecords: Int
    ): ListTasksResponse {
        return ListTasksResponse(
            tasks = tasks.map { toTaskResponse(it) },  // Map ListTasks to TaskResponse
            page = page,
            limit = limit,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }

    // Function to map ListTasks (domain entity) to TaskResponse (API response model)
    private fun toTaskResponse(task: ListTasks): TaskResponse {
        return TaskResponse(
            taskId = task.taskId,
            flowType = task.flowType,
            status = task.status,
            packageReceived = task.packageReceived,
            scheduledSlot = task.scheduledSlot.toString(),  // Convert LocalDateTime to String
            teamId = task.teamId,
            createdAt = task.createdAt.toString(),  // Convert LocalDateTime to String
            updatedAt = task.updatedAt.toString()   // Convert LocalDateTime to String
        )
    }
}
