package `in`.porter.cfms.domain.usecases.entities

import java.math.BigDecimal

data class RecordFranchiseAddressRequest(
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)