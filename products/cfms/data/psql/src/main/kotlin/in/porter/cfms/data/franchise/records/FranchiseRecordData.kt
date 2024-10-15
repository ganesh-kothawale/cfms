package `in`.porter.cfms.data.franchise.records

import `in`.porter.kotlinutils.geos.entities.LatLng
import java.math.BigDecimal
import java.time.Instant

data class FranchiseRecordData(
    val franchiseId : String,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val pocName: String,
    val primaryNumber: String,
    val email: String,
    val status: String,
    val porterHubName: String,
    val franchiseGst: String,
    val franchisePan: String,
    val franchiseCanceledCheque: String,
    val daysOfOperation: String,
    val startTime: String,
    val endTime: String,
    val cutOffTime: String,
    val hlpEnabled: Boolean,
    val radiusCoverage: BigDecimal,
    val showCrNumber: Boolean,
    val kamUser: String,
    val teamId: Int?
)

