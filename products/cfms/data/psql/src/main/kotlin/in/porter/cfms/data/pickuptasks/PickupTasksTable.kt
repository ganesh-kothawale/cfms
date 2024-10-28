package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.holidays.HolidayTable.references
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table

object PickupTasksTable : Table("pickup_details") {
    val pickupTaskId = varchar("pickup_details_id", 10)
    val taskId = varchar ("task_id",10).references(TasksTable.taskId)
    val hlpId = varchar("hlp_id", 10).references(HlpsTable.hlpOrderId)
    val franchiseId = varchar("franchise_id", 10).references(FranchisesTable.franchiseId)
    val orderImages = varchar("order_images", 1000).nullable()
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")
    val packageReceived = integer("package_received").nullable()

    init {
        index(true, taskId)
    }

}