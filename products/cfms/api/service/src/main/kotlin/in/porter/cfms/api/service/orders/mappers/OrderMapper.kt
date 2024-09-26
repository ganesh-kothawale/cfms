package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.Order
import `in`.porter.cfms.domain.orders.entities.Order as DomainOrder

class OrderMapper {
    companion object {
        fun fromDomain(domainOrder: DomainOrder): Order {
            return Order(
                basicDetails = BasicDetailsMapper.fromDomain(domainOrder.basicDetails),
                addressDetails = AddressDetailsMapper.fromDomain(domainOrder.addressDetails),
                itemDetails = ItemDetailsMapper.fromDomain(domainOrder.itemDetails),
                shippingDetails = ShippingDetailsMapper.fromDomain(domainOrder.shippingDetails)
            )
        }
    }
}