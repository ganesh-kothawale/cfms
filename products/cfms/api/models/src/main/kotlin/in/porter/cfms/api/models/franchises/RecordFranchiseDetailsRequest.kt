package `in`.porter.cfms.api.models.franchises

import `in`.porter.cfms.api.models.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant
data class RecordFranchiseDetailsRequest(
    val address: RecordFranchiseAddressRequest,
    val poc: RecordFranchisePOCRequest,
    val customerShipmentLabelRequired: Boolean,
    val radiusCoverage: BigDecimal,
    val hlpEnabled: Boolean,
    val status: FranchiseStatus,
    val daysOfOperation: String,
    val cutOffTime: Instant,
    val startTime: Instant,
    val endTime: Instant,
    val porterHubName: String,
    val franchiseGst: String,
    val franchisePan :String,
    val franchiseCanceledCheque :String,
    val kamUser: String,
    val showCrNumber: Boolean
)