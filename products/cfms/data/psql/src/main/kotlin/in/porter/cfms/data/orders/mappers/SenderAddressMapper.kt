package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.Address
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class SenderAddressMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): Address {
        return Address(
            houseNumber = row[OrdersTable.receiverHomeNumber],
            addressDetails = row[OrdersTable.senderAddress],
            cityName = row[OrdersTable.senderCity],
            stateName = row[OrdersTable.senderState],
            pincode = row[OrdersTable.senderPincode]
        )
    }
}