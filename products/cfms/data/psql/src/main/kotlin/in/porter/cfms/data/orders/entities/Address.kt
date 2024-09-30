package `in`.porter.cfms.data.orders.entities

data class Address(
    val addressDetails: String,
    val cityName: String,
    val pincode: Int,
    val stateName: String,
    val houseNumber: String
)