package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.tasks.CreateTaskRequest
import `in`.porter.cfms.domain.tasks.entities.Tasks
import java.time.Instant
import javax.inject.Inject

class CreateTaskRequestMapper @Inject constructor() {

    fun toDomain(request: CreateTaskRequest, generatedTaskId: String): Tasks {
        return Tasks(
            taskId = generatedTaskId,
            flowType = request.flowType,
            status = request.status,
            packageReceived = request.packageReceived,
            scheduledSlot = request.scheduledSlot,
            teamId = request.teamId,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
