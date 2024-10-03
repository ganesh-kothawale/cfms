package `in`.porter.cfms.api.models.orders

data class Order(
    val basicDetails: BasicDetails,
    val addressDetails: AddressDetails,
    val itemDetails: ItemDetails,
    val shippingDetails: ShippingDetails
)