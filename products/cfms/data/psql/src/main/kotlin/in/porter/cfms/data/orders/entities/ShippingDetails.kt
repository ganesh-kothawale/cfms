package `in`.porter.cfms.data.orders.entities

data class ShippingDetails(
    val shippingLabelLink: String?,
    val pickUpDate: String,
    val volumetricWeight: Int?
)