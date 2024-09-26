package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.CourierTransportDetails
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.CourierTransportDetails as DomainCourierTransportDetails

class CourierTransportDetailsMapper
@Inject constructor() {
    fun map(courierTransportDetails: CourierTransportDetails): DomainCourierTransportDetails {
        return DomainCourierTransportDetails(
            courierPartnerName = courierTransportDetails.courierPartnerName,
            modeOfTransport = courierTransportDetails.modeOfTransport
        )
    }

    companion object {
        fun fromDomain(domainCourierTransportDetails: DomainCourierTransportDetails): CourierTransportDetails {
            return CourierTransportDetails(
                courierPartnerName = domainCourierTransportDetails.courierPartnerName,
                modeOfTransport = domainCourierTransportDetails.modeOfTransport
            )
        }
    }
}