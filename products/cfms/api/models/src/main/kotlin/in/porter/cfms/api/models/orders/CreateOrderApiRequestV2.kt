package `in`.porter.cfms.api.models.orders

data class CreateOrderApiRequestV2(
    val basicDetails: BasicDetails,
    val addressDetails: AddressDetails,
    val itemDetails: ItemDetails,
    val shippingDetails: ShippingDetails
)
