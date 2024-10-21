package `in`.porter.cfms.api.service.pickupTasks.mappers

import `in`.porter.cfms.api.models.TasksStatus as ModelTasksStatus
import `in`.porter.cfms.api.models.pickupTasks.PickupTask as PickupTaskModel
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask as PickupTaskDomain
import `in`.porter.cfms.api.models.pickupTasks.PickupTasksResponse
import `in`.porter.cfms.domain.pickuptasks.TasksStatus as TasksStatusDomain
import javax.inject.Inject

class FetchPickupTasksResponseMapper @Inject constructor() {

    fun toResponse(
        pickupTasks: List<PickupTaskDomain>,
        page: Int,
        size: Int,
        totalPages: Int,
        totalRecords: Int
    ): PickupTasksResponse {
        return PickupTasksResponse(
            pickupTasks = pickupTasks.map { toPickupTaskResponse(it) },
            page = page,
            size = size,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }
    private fun toPickupTaskResponse(pickupTaskDomain: PickupTaskDomain): PickupTaskModel {
        return PickupTaskModel(
            taskId = pickupTaskDomain.taskId,
            orderId = pickupTaskDomain.orderId,
            hlpId = pickupTaskDomain.hlpId,
            franchiseId = pickupTaskDomain.franchiseId,
            status = mapDomainToModelStatus(pickupTaskDomain.status),
            createdAt = pickupTaskDomain.createdAt,
            updatedAt = pickupTaskDomain.updatedAt
        )
    }

    fun mapModelToDomainStatus(status: ModelTasksStatus): TasksStatusDomain =
        when (status) {
            ModelTasksStatus.Pending -> TasksStatusDomain.Pending
            ModelTasksStatus.PickedUp -> TasksStatusDomain.PickedUp
            ModelTasksStatus.Delivered -> TasksStatusDomain.Delivered
        }

    private fun mapDomainToModelStatus(status: TasksStatusDomain): ModelTasksStatus =
        when (status) {
            TasksStatusDomain.Pending -> ModelTasksStatus.Pending
            TasksStatusDomain.PickedUp -> ModelTasksStatus.PickedUp
            TasksStatusDomain.Delivered -> ModelTasksStatus.Delivered
        }
}