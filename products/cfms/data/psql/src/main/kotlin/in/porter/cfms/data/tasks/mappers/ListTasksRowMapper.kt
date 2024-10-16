package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.cfms.data.tasks.records.ListTasksRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasksRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(ListTasksRowMapper::class.java)

    fun toRecord(resultRow: ResultRow): ListTasksRecord {
        logger.info("Mapping result row to ListTasksRecord")

        return ListTasksRecord(
            taskId = resultRow[TasksTable.taskId],
            flowType = resultRow[TasksTable.flowType],
            status = resultRow[TasksTable.status],
            packageReceived = resultRow[TasksTable.packageReceived],
            scheduledSlot = resultRow[TasksTable.scheduledSlot].toString(),  // Convert to string
            teamId = resultRow[TasksTable.teamId],
            createdAt = resultRow[TasksTable.createdAt].toString(),
            updatedAt = resultRow[TasksTable.updatedAt].toString()
        )
    }
}