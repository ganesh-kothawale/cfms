package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.ReceiverDetails
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails as DomainReceiverDetails
import javax.inject.Inject

class ReceiverDetailsMapper
@Inject constructor(
    private val personalInfoMapper: PersonalInfoMapper,
    private val addressMapper: AddressMapper,
) {
    fun map(receiverDetails: ReceiverDetails): DomainReceiverDetails {
        return DomainReceiverDetails(
            personalInfo = personalInfoMapper.map(receiverDetails.personalInfo),
            address = addressMapper.map(receiverDetails.address),
        )
    }

    fun fromDomain(domainReceiverDetails: DomainReceiverDetails): ReceiverDetails {
        return ReceiverDetails(
            personalInfo = personalInfoMapper.fromDomain(domainReceiverDetails.personalInfo),
            address = addressMapper.fromDomain(domainReceiverDetails.address)
        )


    }
}