package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.data.tasks.records.ListTaskRecord
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import javax.inject.Inject

class ListTaskMapper @Inject constructor() {

    // Convert ListTaskRecord to domain ListTasks entity
    fun toDomain(listTaskRecord: ListTaskRecord): ListTasks {
        return ListTasks(
            taskId = listTaskRecord.taskId,
            flowType = listTaskRecord.flowType,
            status = listTaskRecord.status,
            createdAt = listTaskRecord.createdAt,
            updatedAt = listTaskRecord.updatedAt,
            pickupTaskEntity = listTaskRecord.pickupTaskEntity,
            reconEntity = listTaskRecord.reconEntity
        )
    }

    // Convert domain ListTasks entity to ListTaskRecord for persistence
    fun toRecord(listTask: ListTasks): ListTaskRecord {
        return ListTaskRecord(
            taskId = listTask.taskId,
            flowType = listTask.flowType,
            status = listTask.status,
            createdAt = listTask.createdAt,
            updatedAt = listTask.updatedAt,
            pickupTaskEntity = listTask.pickupTaskEntity,
            reconEntity = listTask.reconEntity
        )
    }
}
