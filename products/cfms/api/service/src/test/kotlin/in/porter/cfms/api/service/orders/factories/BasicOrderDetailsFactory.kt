package `in`.porter.cfms.api.service.orders.factories

import `in`.porter.cfms.api.models.orders.BasicDetails as ApiBasicDetails
import `in`.porter.cfms.api.models.orders.AssociationDetails as ApiAssociationDetails
import `in`.porter.cfms.api.models.orders.CourierTransportDetails as ApiCourierTransportDetails
import `in`.porter.cfms.domain.orders.entities.BasicDetails as DomainBasicDetails
import `in`.porter.cfms.domain.orders.entities.AssociationDetails as DomainAssociationDetails
import `in`.porter.cfms.domain.orders.entities.CourierTransportDetails as DomainCourierTransportDetails

object BasicOrderDetailsFactory {

    fun buildApiAssociationDetails(): ApiAssociationDetails {
        return ApiAssociationDetails(
            franchiseId = "franchise123",
            teamId = "team456"
        )
    }

    fun buildDomainAssociationDetails(): DomainAssociationDetails {
        return DomainAssociationDetails(
            franchiseId = "franchise123",
            teamId = "team456"
        )
    }

    fun buildApiCourierTransportDetails(): ApiCourierTransportDetails {
        return ApiCourierTransportDetails(
            courierPartnerName = "CourierPartner",
            modeOfTransport = "Air"
        )
    }

    fun buildDomainCourierTransportDetails(): DomainCourierTransportDetails {
        return DomainCourierTransportDetails(
            courierPartnerName = "CourierPartner",
            modeOfTransport = "Air"
        )
    }

    fun buildApiBasicDetails(): ApiBasicDetails {
        return ApiBasicDetails(
            associationDetails = buildApiAssociationDetails(),
            orderNumber = "order789",
            awbNumber = "awb123",
            accountId = 1,
            accountCode = "accountCode123",
            courierTransportDetails = buildApiCourierTransportDetails(),
            orderStatus = "Delivered"
        )
    }

    fun buildDomainBasicDetails(): DomainBasicDetails {
        return DomainBasicDetails(
            associationDetails = buildDomainAssociationDetails(),
            orderNumber = "order789",
            awbNumber = "awb123",
            accountId = 1,
            accountCode = "accountCode123",
            courierTransportDetails = buildDomainCourierTransportDetails(),
            orderStatus = "Delivered"
        )
    }
}