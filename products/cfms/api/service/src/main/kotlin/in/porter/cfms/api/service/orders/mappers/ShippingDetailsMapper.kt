package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.ShippingDetails
import `in`.porter.cfms.domain.orders.entities.ShippingDetails as DomainShippingDetails
import javax.inject.Inject

class ShippingDetailsMapper
@Inject constructor() {
    fun map(shippingDetails: ShippingDetails): DomainShippingDetails {
        return DomainShippingDetails(
            shippingLabelLink = shippingDetails.shippingLabelLink,
            pickUpDate = shippingDetails.pickUpDate,
            volumetricWeight = shippingDetails.volumetricWeight
        )
    }
}