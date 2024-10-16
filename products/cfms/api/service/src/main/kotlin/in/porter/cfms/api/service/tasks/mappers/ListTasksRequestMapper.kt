package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.tasks.ListTasksRequest
import javax.inject.Inject

class ListTasksRequestMapper @Inject constructor() {

    fun toDomain(page: Int, limit: Int): ListTasksRequest {
        return ListTasksRequest(
            page = page,
            limit = limit
        )
    }
}