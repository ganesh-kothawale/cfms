package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.AddressDetails
import `in`.porter.cfms.domain.orders.entities.AddressDetails as DomainAddressDetails
import javax.inject.Inject

class AddressDetailsMapper
@Inject constructor(
    private val senderDetailsMapper: SenderDetailsMapper,
    private val receiverDetailsMapper: ReceiverDetailsMapper
) {
    fun map(addressDetails: AddressDetails): DomainAddressDetails {
        return DomainAddressDetails(
            senderDetails = senderDetailsMapper.map(addressDetails.senderDetails),
            receiverDetails = receiverDetailsMapper.map(addressDetails.receiverDetails)
        )
    }

    fun fromDomain(domainAddressDetails: DomainAddressDetails): AddressDetails {
        return AddressDetails(
            senderDetails = senderDetailsMapper.fromDomain(domainAddressDetails.senderDetails),
            receiverDetails = receiverDetailsMapper.fromDomain(domainAddressDetails.receiverDetails)
        )

    }
}