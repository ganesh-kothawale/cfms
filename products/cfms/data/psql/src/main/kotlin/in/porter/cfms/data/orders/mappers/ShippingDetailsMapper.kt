package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.ShippingDetails
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
}