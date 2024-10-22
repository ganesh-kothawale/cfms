package `in`.porter.cfms.api.service.pickupTasks.mappers

import `in`.porter.cfms.api.models.tasks.ListTasksRequest
import javax.inject.Inject

class FetchPickupTasksRequestMapper @Inject constructor() {
    fun toDomain(page: Int, size: Int): ListTasksRequest {
        return ListTasksRequest(
            page = page,
            size = size
        )
    }
}