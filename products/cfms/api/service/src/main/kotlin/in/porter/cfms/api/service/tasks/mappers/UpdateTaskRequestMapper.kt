package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.tasks.UpdateTaskRequest
import `in`.porter.cfms.domain.tasks.entities.Tasks
import java.time.Instant
import javax.inject.Inject

class UpdateTaskRequestMapper @Inject constructor() {

    fun toDomain(request: UpdateTaskRequest): Tasks {
        return Tasks(
            taskId = request.taskId,
            flowType = request.flowType,
            status = request.status,
            packageReceived = request.packageReceived,
            scheduledSlot = request.scheduledSlot,
            teamId = request.teamId,
            createdAt = Instant.now(),
            updatedAt = Instant.now() // Update the timestamp for the update action
        )
    }
}
