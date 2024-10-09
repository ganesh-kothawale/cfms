package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.Address
import `in`.porter.cfms.domain.orders.entities.Address as DomainAddress
import `in`.porter.cfms.data.orders.repos.OrdersTable
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

    fun toDomain(entity: Address): DomainAddress {
        return DomainAddress(
            houseNumber = entity.houseNumber,
            addressDetails = entity.addressDetails,
            cityName = entity.cityName,
            stateName = entity.stateName,
            pincode = entity.pincode
        )
    }
}