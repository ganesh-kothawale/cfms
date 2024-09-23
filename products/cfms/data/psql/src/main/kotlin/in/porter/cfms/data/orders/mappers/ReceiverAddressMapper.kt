package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.Address
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ReceiverAddressMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): Address {
        return Address(
            addressDetails = row[OrdersTable.senderAddress],
            cityName = row[OrdersTable.receiverCity],
            stateName = row[OrdersTable.receiverState],
            houseNumber = row[OrdersTable.receiverHomeNumber],
            pincode = row[OrdersTable.receiverPincode]
        )
    }
}