package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.cfms.data.tasks.records.TaskRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class TaskRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(TaskRowMapper::class.java)

    fun toRecord(row: ResultRow): TaskRecord {
        logger.info("Mapping result row to TaskRecord")
        return TaskRecord(
            taskId = row[TasksTable.taskId],
            flowType = row[TasksTable.flowType],
            status = row[TasksTable.status],
            packageReceived = row[TasksTable.packageReceived],
            scheduledSlot = row[TasksTable.scheduledSlot],
            teamId = row[TasksTable.teamId],
            createdAt = row[TasksTable.createdAt],
            updatedAt = row[TasksTable.updatedAt]
        )
    }
}
