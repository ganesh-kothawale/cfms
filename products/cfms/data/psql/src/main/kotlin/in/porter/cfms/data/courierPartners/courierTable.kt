package `in`.porter.cfms.data.courierPartners

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable

object CourierPartnersTable: IntIdTable("courier") {
    val name = varchar("name", 255)
    val createdAt = timestampWithoutTZAsInstant("created_at")
}