package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.BasicDetails
import `in`.porter.cfms.domain.orders.entities.BasicDetails as DomainBasicDetails
import `in`.porter.cfms.data.orders.repos.OrdersTable
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

    fun toDomain(entity: BasicDetails): DomainBasicDetails {
        return DomainBasicDetails(
            awbNumber = entity.awbNumber,
            courierTransportDetails = courierTransportDetailsMapper.toDomain(entity.courierTransportDetails),
            orderStatus = entity.orderStatus,
            associationDetails = associationDetailsMapper.toDomain(entity.associationDetails),
            accountId = entity.accountId,
            accountCode = entity.accountCode,
            orderNumber = entity.orderNumber
        )
    }
}