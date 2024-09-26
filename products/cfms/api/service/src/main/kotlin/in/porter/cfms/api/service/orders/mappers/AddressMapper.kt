package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.Address
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.Address as DomainAddress

class AddressMapper
@Inject constructor() {
    fun map(address: Address): DomainAddress {
        return DomainAddress(
            houseNumber = address.houseNumber,
            addressDetails = address.addressDetails,
            cityName = address.cityName,
            stateName = address.stateName,
            pincode = address.pincode
        )
    }

    companion object {
        fun fromDomain(domainAddress: DomainAddress): Address {
            return Address(
                houseNumber = domainAddress.houseNumber,
                addressDetails = domainAddress.addressDetails,
                cityName = domainAddress.cityName,
                stateName = domainAddress.stateName,
                pincode = domainAddress.pincode
            )
        }
    }
}