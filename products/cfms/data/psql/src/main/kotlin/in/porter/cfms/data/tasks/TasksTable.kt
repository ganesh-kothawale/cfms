package `in`.porter.cfms.data.tasks

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table

object TasksTable : Table("tasks") {
    val taskId = integer("task_id").autoIncrement().primaryKey()
    val flowType = varchar("flow_type", 50)
    val status = varchar("status", 50)
    val packageReceived = integer("package_received")
    val scheduledSlot = timestampWithoutTZAsInstant("scheduled_slot")
    val teamId = integer("team_id") // Assuming there is a TeamsTable
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")

    // Indexes if required
    init {
        index(true, taskId)
    }
}
