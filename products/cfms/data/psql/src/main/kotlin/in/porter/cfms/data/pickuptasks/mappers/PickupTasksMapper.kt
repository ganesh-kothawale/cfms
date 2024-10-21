package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.pickuptasks.records.PickupTaskRecord
import `in`.porter.cfms.domain.pickuptasks.TasksStatus
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import javax.inject.Inject

class PickupTasksMapper @Inject constructor() {
    fun toDomain(record: PickupTaskRecord): PickupTask {
        return PickupTask(
            taskId = record.taskId,
            orderId = record.orderId,
            hlpId = record.hlpId,
            franchiseId = record.franchiseId,
            status = TasksStatus.valueOf(record.status),
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }
}