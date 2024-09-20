package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.SenderDetails
import `in`.porter.cfms.domain.orders.entities.SenderDetails as DomainSenderDetails
import javax.inject.Inject

class SenderDetailsMapper
@Inject constructor(
    private val personalInfoMapper: PersonalInfoMapper,
    private val addressMapper: AddressMapper,
    private val locationMapper: LocationMapper
) {
    fun map(senderDetails: SenderDetails): DomainSenderDetails {
        return DomainSenderDetails(
            personalInfo = personalInfoMapper.map(senderDetails.personalInfo),
            address = addressMapper.map(senderDetails.address),
            location = locationMapper.map(senderDetails.location)
        )
    }
}