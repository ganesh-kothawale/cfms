package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table

object PickupImageMappingTable : Table("pickup_image_mapping") {

    val id = integer("id").autoIncrement()
    val taskId = varchar("task_id", 50).references(TasksTable.taskId)
    val pickupDetailsId = varchar("pickup_details_id", 50).references(PickupTasksTable.pickupTaskId)
    val orderImage = text("order_image")
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")
    override val primaryKey = PrimaryKey(id, name = "PK_PickupImageMapping_Id")
}
