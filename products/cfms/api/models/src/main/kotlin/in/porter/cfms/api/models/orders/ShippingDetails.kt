package `in`.porter.cfms.api.models.orders

data class ShippingDetails(
    val shippingLabelLink: String?,  // nullable
    val pickUpDate: String,
    val volumetricWeight: Int?  // nullable
)