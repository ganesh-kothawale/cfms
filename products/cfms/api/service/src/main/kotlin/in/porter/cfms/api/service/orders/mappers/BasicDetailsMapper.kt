package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.BasicDetails
import `in`.porter.cfms.domain.orders.entities.BasicDetails as DomainBasicDetails
import javax.inject.Inject

class BasicDetailsMapper
@Inject constructor(
    private val associationDetailsMapper: AssociationDetailsMapper,
    private val courierTransportDetailsMapper: CourierTransportDetailsMapper
) {
    fun map(basicDetails: BasicDetails): DomainBasicDetails {
        return DomainBasicDetails(
            associationDetails = associationDetailsMapper.map(basicDetails.associationDetails),
            orderNumber = basicDetails.orderNumber,
            awbNumber = basicDetails.awbNumber,
            accountId = basicDetails.accountId,
            accountCode = basicDetails.accountCode,
            courierTransportDetails = courierTransportDetailsMapper.map(basicDetails.courierTransportDetails),
            orderStatus = basicDetails.orderStatus
        )
    }

    companion object {
        fun fromDomain(domainBasicDetails: DomainBasicDetails): BasicDetails {
            return BasicDetails(
                associationDetails = AssociationDetailsMapper.fromDomain(domainBasicDetails.associationDetails),
                orderNumber = domainBasicDetails.orderNumber,
                awbNumber = domainBasicDetails.awbNumber,
                accountId = domainBasicDetails.accountId,
                accountCode = domainBasicDetails.accountCode,
                courierTransportDetails = CourierTransportDetailsMapper.fromDomain(domainBasicDetails.courierTransportDetails),
                orderStatus = domainBasicDetails.orderStatus
            )
        }
    }
}