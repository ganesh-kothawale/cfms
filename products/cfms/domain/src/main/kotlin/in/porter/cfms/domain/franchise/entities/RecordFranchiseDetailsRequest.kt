package `in`.porter.cfms.domain.franchise.entities


import `in`.porter.cfms.domain.franchise.FranchiseStatus
import java.math.BigDecimal


public final data class RecordFranchiseDetailsRequest(
    val address: RecordFranchiseAddressRequest,
    val poc: RecordFranchisePOCRequest,
    val franchiseId: String,
    val customerShipmentLabelRequired: Boolean,
    val radiusCoverage: BigDecimal,
    val hlpEnabled: Boolean,
    val status: FranchiseStatus,
    val daysOfOperation: String,
    val cutOffTime: String,
    val startTime: String,
    val endTime: String,
    val porterHubName: String,
    val franchiseGst: String,
    val franchisePan: String,
    val franchiseCanceledCheque: String,
    val kamUser: String,
    val showCrNumber: Boolean
)
