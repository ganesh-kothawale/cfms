package orders.mappers

import `in`.porter.cfms.api.models.orders.Address
import `in`.porter.cfms.domain.orders.entities.Address as DomainAddress

class AddressMapper {
    fun map(address: Address): DomainAddress {
        return DomainAddress(
            houseNumber = address.houseNumber,
            addressDetails = address.addressDetails,
            cityName = address.cityName,
            stateName = address.stateName,
            pincode = address.pincode
        )
    }
}