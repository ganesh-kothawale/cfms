package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.Order
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.Order as DomainOrder

class OrderMapper @Inject constructor(
    private val basicDetailsMapper: BasicDetailsMapper,
    private val addressDetailsMapper: AddressDetailsMapper,
    private val itemDetailsMapper: ItemDetailsMapper,
    private val shippingDetailsMapper: ShippingDetailsMapper
) {
    fun fromDomain(domainOrder: DomainOrder): Order {
        return Order(
            basicDetails = basicDetailsMapper.fromDomain(domainOrder.basicDetails),
            addressDetails = addressDetailsMapper.fromDomain(domainOrder.addressDetails),
            itemDetails = itemDetailsMapper.fromDomain(domainOrder.itemDetails),
            shippingDetails = shippingDetailsMapper.fromDomain(domainOrder.shippingDetails)
        )
    }
}