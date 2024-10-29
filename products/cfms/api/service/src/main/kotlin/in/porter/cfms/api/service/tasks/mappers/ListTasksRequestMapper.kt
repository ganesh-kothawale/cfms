package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.tasks.ListTasksRequest
import `in`.porter.cfms.domain.tasks.entities.DomainListTasksRequest
import javax.inject.Inject

class ListTasksRequestMapper
@Inject
constructor() {
    fun toDomain(request: ListTasksRequest): DomainListTasksRequest {
        return DomainListTasksRequest(
            page = request.page,
            size = request.size,
            createdDate = request.createdDate,
            updatedDate = request.updatedDate,
            taskType = request.taskType,
            franchiseIds = request.franchiseIds,
            orderIds = request.orderIds,
            awbNumbers = request.awbNumbers,
            taskStatus = request.taskStatus
        )
    }
}
