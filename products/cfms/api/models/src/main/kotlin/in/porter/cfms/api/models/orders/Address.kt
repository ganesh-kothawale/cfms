package `in`.porter.cfms.api.models.orders

data class Address(
    val houseNumber: String,
    val addressDetails: String,
    val cityName: String,
    val stateName: String,
    val pincode: Int,
    val location: Location?
)