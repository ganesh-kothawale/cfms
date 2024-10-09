package `in`.porter.cfms.api.models.franchises

import com.fasterxml.jackson.annotation.JsonProperty
import `in`.porter.cfms.api.models.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant
data class RecordFranchiseDetailsRequest(
    val address: RecordFranchiseAddressRequest,
    val poc: RecordFranchisePOCRequest,
    @JsonProperty("customer_shipment_label_required") val customerShipmentLabelRequired: Boolean,
    @JsonProperty("radius_covered") val radiusCoverage: BigDecimal,
    @JsonProperty("hlp_enabled") val hlpEnabled: Boolean,
    val status: FranchiseStatus,
    @JsonProperty("days_of_the_week") val daysOfOperation: String,
    @JsonProperty("cut_off_time")
    val cutOffTime: Instant,
    @JsonProperty("start_time")
    val startTime: Instant,
    @JsonProperty("end_time")
    val endTime: Instant,
    @JsonProperty("porter_hub_name") val porterHubName: String,
    @JsonProperty("franchise_gst") val franchiseGst: String,
    @JsonProperty("franchise_pan") val franchisePan :String,
    @JsonProperty("franchise_canceled_cheque") val franchiseCanceledCheque :String,
    @JsonProperty("kam_user") val kamUser: String,
    @JsonProperty("show_cr_number") val showCrNumber: Boolean
)