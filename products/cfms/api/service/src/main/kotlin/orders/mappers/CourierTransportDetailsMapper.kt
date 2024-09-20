package orders.mappers

import `in`.porter.cfms.api.models.orders.CourierTransportDetails
import `in`.porter.cfms.domain.orders.entities.CourierTransportDetails as DomainCourierTransportDetails

class CourierTransportDetailsMapper {
    fun map(courierTransportDetails: CourierTransportDetails): DomainCourierTransportDetails {
        return DomainCourierTransportDetails(
            courierPartnerName = courierTransportDetails.courierPartnerName,
            modeOfTransport = courierTransportDetails.modeOfTransport
        )
    }
}