package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.cfms.domain.tasks.entities.Tasks
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasksMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(ListTasksMapper::class.java)

    fun toDomain(record: TaskRecord): Tasks {
        logger.info("Mapping record to domain: $record")
        return Tasks(
            taskId = record.taskId,
            flowType = record.flowType,
            status = record.status,
            packageReceived = record.packageReceived,
            scheduledSlot = record.scheduledSlot,
            teamId = record.teamId,
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }
}
