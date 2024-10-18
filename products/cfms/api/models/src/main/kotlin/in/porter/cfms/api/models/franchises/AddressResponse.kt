package `in`.porter.cfms.api.models.franchises

import java.math.BigDecimal

data class AddressResponse(
    val lat: BigDecimal,
    val lng: BigDecimal,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String
)
