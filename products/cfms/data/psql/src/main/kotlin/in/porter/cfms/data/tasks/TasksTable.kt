package `in`.porter.cfms.data.tasks

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.or

object TasksTable : Table("tasks") {
    val id = integer("id").autoIncrement()
    val taskId = varchar("task_id",10).uniqueIndex()
    val flowType = varchar("flow_type", 50)
    val status = varchar("status", 50).check { it eq "Pending" or (it eq "In Progress") or (it eq "Completed") or (it eq "Failed") }
    val packageReceived = integer("package_received").nullable()
    val scheduledSlot = timestampWithoutTZAsInstant("scheduled_slot").nullable()
    val teamId = varchar("team_id",10)
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")

    override val primaryKey = PrimaryKey(id, name = "PK_Tasks_ID")
    init {
        index(true, taskId)
    }
}
