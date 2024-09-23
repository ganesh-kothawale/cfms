package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.CourierTransportDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class CourierTransportDetailsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): CourierTransportDetails {
        return CourierTransportDetails(
            courierPartnerName = row[OrdersTable.courierPartner],
            modeOfTransport = row[OrdersTable.modeOfTransport]

        )
    }
}