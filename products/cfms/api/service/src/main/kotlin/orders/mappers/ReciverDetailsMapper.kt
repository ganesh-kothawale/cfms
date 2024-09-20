package orders.mappers

import `in`.porter.cfms.api.models.orders.ReceiverDetails
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails as DomainReceiverDetails
import javax.inject.Inject

class ReceiverDetailsMapper
@Inject constructor(
    private val personalInfoMapper: PersonalInfoMapper,
    private val addressMapper: AddressMapper,
    private val locationMapper: LocationMapper
) {
    fun map(receiverDetails: ReceiverDetails): DomainReceiverDetails {
        return DomainReceiverDetails(
            personalInfo = personalInfoMapper.map(receiverDetails.personalInfo),
            address = addressMapper.map(receiverDetails.address),
            location = receiverDetails.location?.let { locationMapper.map(it) },
        )
    }
}