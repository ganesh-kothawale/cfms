package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.Location
import `in`.porter.cfms.domain.orders.entities.Location as DomainLocation
import `in`.porter.cfms.data.orders.repos.OrdersTable
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class SenderLocationMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): Location {
        return Location(
            latitude = row[OrdersTable.senderLatitude],
            longitude = row[OrdersTable.senderLongitude]
        )
    }

    fun toDomain(entity: Location): DomainLocation {
        return DomainLocation(
            latitude = entity.latitude,
            longitude = entity.longitude
        )
    }
}