package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.BasicDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class BasicDetailsMapper @Inject constructor(
    private val courierTransportDetailsMapper: CourierTransportDetailsMapper,
    private val associationDetailsMapper: AssociationDetailsMapper
) {
    fun fromResultRow(row: ResultRow): BasicDetails {
        print("this is query result")
        return BasicDetails(
            awbNumber = row[OrdersTable.awbNumber],
            courierTransportDetails = courierTransportDetailsMapper.fromResultRow(row),
            orderStatus = row[OrdersTable.orderStatus],
            associationDetails = associationDetailsMapper.fromResultRow(row),
            accountId = row[OrdersTable.accountId],
            accountCode = row[OrdersTable.accountCode],
            orderNumber = row[OrdersTable.orderNumber]
        )
    }
}