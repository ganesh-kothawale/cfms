package `in`.porter.cfms.data.orders.entities

data class Order(
    val basicDetails: BasicDetails,
    val addressDetails: AddressDetails,
    val itemDetails: ItemDetails,
    val shippingDetails: ShippingDetails
)