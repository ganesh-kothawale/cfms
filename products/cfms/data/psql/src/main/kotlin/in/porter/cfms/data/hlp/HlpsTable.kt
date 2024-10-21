package `in`.porter.cfms.data.hlp

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable

object HlpsTable : IntIdTable("hlps") {
    val hlpOrderId = varchar("hlp_order_id", 64)
    val hlpOrderStatus = varchar("hlp_order_status", 64).nullable()
    val otp = varchar("otp", 64).nullable()
    val riderName = varchar("rider_name", 128).nullable()
    val riderNumber = varchar("rider_number", 64).nullable()
    val vehicleType = varchar("vehicle_type", 64).nullable()
    val franchiseId = varchar("franchise_id", 64)
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")
}
