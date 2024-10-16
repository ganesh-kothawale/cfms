package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.tasks.UpdateTaskStatusRequest
import `in`.porter.cfms.domain.tasks.entities.UpdateTaskStatus
import javax.inject.Inject

class UpdateTaskStatusRequestMapper @Inject constructor() {

    fun toDomain(request: UpdateTaskStatusRequest): UpdateTaskStatus {
        return UpdateTaskStatus(
            taskIds = request.taskIds,
            status = request.status
        )
    }
}
