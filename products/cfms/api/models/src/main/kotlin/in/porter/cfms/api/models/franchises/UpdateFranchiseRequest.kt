package `in`.porter.cfms.api.models.franchises

import com.fasterxml.jackson.annotation.JsonProperty
import `in`.porter.cfms.api.models.FranchiseStatus
import java.math.BigDecimal

data class UpdateFranchiseRequest(
    val franchiseId: String,
    val address: RecordFranchiseAddressRequest,
    val poc: RecordFranchisePOCRequest,
    @JsonProperty("radius_coverage")
    val radiusCovered: BigDecimal,
    @JsonProperty("hlp_enabled")
    val hlpEnabled: Boolean,
    @JsonProperty("is_active")
    val isActive: Boolean,
    @JsonProperty("days_of_operation")
    val daysOfOperation: String?,
    @JsonProperty("cut_off_time")
    val cutOffTime: String,
    @JsonProperty("start_time")
    val startTime: String,
    @JsonProperty("end_time")
    val endTime: String,
    @JsonProperty("porter_hub_name")
    val porterHubName: String,
    @JsonProperty("franchise_gst")
    val franchiseGst: String,
    @JsonProperty("franchise_pan")
    val franchisePan: String,
    @JsonProperty("franchise_canceled_cheque")
    val franchiseCanceledCheque: String,
    @JsonProperty("courier_partners")
    val courierPartners: List<String>,
    @JsonProperty("kam_user")
    val kamUser: String,
    @JsonProperty("customer_shipment_label_required")
    val customerShipmentLabelRequired: Boolean?,
    @JsonProperty("show_cr_number")
    val showCrNumber: Boolean,
    val status: FranchiseStatus,
    @JsonProperty("days_of_the_week")
    val daysOfTheWeek: String?,
    @JsonProperty("team_id")
    val teamId: Int?
)