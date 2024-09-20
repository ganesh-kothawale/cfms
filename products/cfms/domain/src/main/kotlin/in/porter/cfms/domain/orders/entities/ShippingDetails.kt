package `in`.porter.cfms.domain.orders.entities

data class ShippingDetails(
    val shippingLabelLink: String?,  // nullable
    val pickUpDate: String,
    val volumetricWeight: Int?  // nullable
)