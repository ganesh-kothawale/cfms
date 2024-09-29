package `in`.porter.cfms.domain.orders.entities

data class CreateOrderRequest(
    val basicDetails: BasicDetails,
    val addressDetails: AddressDetails,
    val itemDetails: ItemDetails,
    val shippingDetails: ShippingDetails
)
