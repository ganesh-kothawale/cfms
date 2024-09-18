package `in`.porter.cfms.api.models.franchises


import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import `in`.porter.cfms.api.models.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant


data class RecordFranchiseDetailsRequest(
    val address: RecordFranchiseAddressRequest,
    val poc: RecordFranchisePOCRequest,
    @JsonProperty("customerShipmentLabelRequired") val customerShipmentLabelRequired: Boolean,
    @JsonProperty("radiusCoverage") val radiusCoverage: BigDecimal,
    @JsonProperty("hlpEnabled") val hlpEnabled: Boolean,
    val status: FranchiseStatus,
    @JsonProperty("daysOfOperation") val daysOfOperation: String,
    @JsonProperty("cutOffTime")
    val cutOffTime: Instant,
    @JsonProperty("startTime")
    val startTime: Instant,
    @JsonProperty("endTime")
    val endTime: Instant,
    @JsonProperty("porterHubName") val porterHubName: String,
    @JsonProperty("franchiseGst") val franchiseGst: String,
    @JsonProperty("franchisePan") val franchisePan :String,
    @JsonProperty("franchiseCanceledCheque") val franchiseCanceledCheque :String,
    @JsonProperty("kamUser") val kamUser: String,
    @JsonProperty("showCrNumber") val showCrNumber: Boolean
)
