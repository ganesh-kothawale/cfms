package `in`.porter.cfms.domain.franchise.entities

import java.math.BigDecimal

data class Address(
    val lat: BigDecimal,
    val lng: BigDecimal,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String
)
