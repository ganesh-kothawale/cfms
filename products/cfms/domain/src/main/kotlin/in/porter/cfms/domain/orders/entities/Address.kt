package `in`.porter.cfms.domain.orders.entities

data class Address(
    val houseNumber: String,
    val addressDetails: String,
    val cityName: String,
    val stateName: String,
    val pincode: Int,
)