package `in`.porter.cfms.domain.usecases.entities


import `in`.porter.cfms.domain.franchise.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant


public final data class RecordFranchiseDetailsRequest(
    val address: RecordFranchiseAddressRequest,
    val poc: RecordFranchisePOCRequest,
    val franchiseId: String,
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
    val franchisePan: String,
    val franchiseCanceledCheque: String,
    val kamUser: String,
    val showCrNumber: Boolean
)
