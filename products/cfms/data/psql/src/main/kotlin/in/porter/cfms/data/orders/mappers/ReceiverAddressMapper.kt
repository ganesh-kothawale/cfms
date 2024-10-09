package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.Address
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.domain.orders.entities.Address as DomainAddress
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

    fun toDomain(entity: Address): DomainAddress {
        return DomainAddress(
            addressDetails = entity.addressDetails,
            cityName = entity.cityName,
            stateName = entity.stateName,
            houseNumber = entity.houseNumber,
            pincode = entity.pincode
        )
    }
}