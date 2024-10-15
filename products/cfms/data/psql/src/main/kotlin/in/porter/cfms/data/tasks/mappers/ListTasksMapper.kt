package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.data.tasks.records.ListTasksRecord
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasksMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(ListTasksMapper::class.java)

    fun toDomain(record: ListTasksRecord): ListTasks {
        logger.info("Mapping record to domain: $record")
        return ListTasks(
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
