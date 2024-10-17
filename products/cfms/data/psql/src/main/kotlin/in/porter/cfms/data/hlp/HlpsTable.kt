package `in`.porter.cfms.data.hlp

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable

object HlpsTable : IntIdTable("hlps") {
    val hlpOrderId = varchar("hlp_order_id", 50)
    val hlpOrderStatus = varchar("hlp_order_status", 50)
    val otp = varchar("otp", 50)
    val riderName = varchar("rider_name", 50)
    val riderNumber = varchar("rider_number", 50)
    val vehicleType = varchar("vehicle_type", 50)
    val franchiseId = varchar("franchise_id", 50)
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")
}
