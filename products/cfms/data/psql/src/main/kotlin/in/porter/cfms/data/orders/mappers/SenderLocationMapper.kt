package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.Location
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class SenderLocationMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): Location {
        return Location(
            latitude = row[OrdersTable.senderLatitude],
            longitude = row[OrdersTable.senderLongitude]
        )
    }
}