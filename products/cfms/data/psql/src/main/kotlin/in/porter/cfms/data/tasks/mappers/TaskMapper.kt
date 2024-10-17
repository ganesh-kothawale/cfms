package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.cfms.domain.tasks.entities.Tasks
import java.time.Instant
import javax.inject.Inject

class TaskMapper @Inject constructor() {

    // Convert TaskRecord to domain Tasks entity
    fun toDomain(taskRecord: TaskRecord): Tasks {
        return Tasks(
            taskId = taskRecord.taskId,
            flowType = taskRecord.flowType,
            status = taskRecord.status,
            packageReceived = taskRecord.packageReceived,
            scheduledSlot = taskRecord.scheduledSlot,
            teamId = taskRecord.teamId,
            createdAt = taskRecord.createdAt,
            updatedAt = taskRecord.updatedAt
        )
    }

    // Convert domain Tasks entity to TaskRecord for persistence
    fun toRecord(task: Tasks): TaskRecord {
        return TaskRecord(
            taskId = task.taskId,
            flowType = task.flowType,
            status = task.status,
            packageReceived = task.packageReceived,
            scheduledSlot = task.scheduledSlot,
            teamId = task.teamId,
            createdAt = task.createdAt,
            updatedAt = task.updatedAt ?: Instant.now()
        )
    }
}
