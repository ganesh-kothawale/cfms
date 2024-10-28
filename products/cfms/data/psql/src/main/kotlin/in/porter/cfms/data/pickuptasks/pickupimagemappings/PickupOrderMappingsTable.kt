package `in`.porter.cfms.data.pickuptasks.pickupimagemappings

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table

object PickupOrderMappingsTable : Table("pickup_order_mappings") {
    val id = integer("id").autoIncrement().primaryKey()
    val pickupTaskId = varchar("pickup_details_id", 10).references(PickupTasksTable.pickupTaskId)
    val mappingId = varchar("mapping_id", length = 255).uniqueIndex()
    val orderId = varchar("order_id", length = 255).references(OrdersTable.orderId)
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")
}