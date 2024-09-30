package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.orders.entities.CourierTransportDetails
import `in`.porter.cfms.domain.orders.entities.CourierTransportDetails as DomainCourierTransportDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class CourierTransportDetailsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): CourierTransportDetails {
        return CourierTransportDetails(
            courierPartnerName = row[OrdersTable.courierPartner],
            modeOfTransport = row[OrdersTable.modeOfTransport]
        )
    }

    fun toDomain(entity: CourierTransportDetails): DomainCourierTransportDetails {
        return DomainCourierTransportDetails(
            courierPartnerName = entity.courierPartnerName,
            modeOfTransport = entity.modeOfTransport
        )
    }
}