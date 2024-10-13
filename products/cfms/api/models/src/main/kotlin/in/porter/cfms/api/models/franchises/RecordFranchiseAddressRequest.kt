package `in`.porter.cfms.api.models.franchises

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class RecordFranchiseAddressRequest(
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    @JsonProperty("lat") val latitude: BigDecimal,
    @JsonProperty("lng") val longitude: BigDecimal
)
