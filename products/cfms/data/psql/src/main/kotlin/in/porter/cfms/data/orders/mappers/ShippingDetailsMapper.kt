package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.ShippingDetails
import `in`.porter.cfms.domain.orders.entities.ShippingDetails as DomainShippingDetails
import `in`.porter.cfms.data.orders.repos.OrdersTable
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ShippingDetailsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): ShippingDetails {
        return ShippingDetails(
            shippingLabelLink = row[OrdersTable.shippingLabelLink],
            pickUpDate = row[OrdersTable.pickupDate],
            volumetricWeight = row[OrdersTable.volumetricWeight]
        )
    }

    fun toDomain(entity: ShippingDetails): DomainShippingDetails {
        return DomainShippingDetails(
            shippingLabelLink = entity.shippingLabelLink,
            pickUpDate = entity.pickUpDate,
            volumetricWeight = entity.volumetricWeight
        )
    }
}