package `in`.porter.cfms.domain.franchise.entities

import `in`.porter.cfms.domain.franchise.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant

data class UpdateFranchise(
    val franchiseId: String,
    val pocName: String,
    val primaryNumber: String,
    val email: String,
    val address: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val city: String,
    val state: String,
    val pincode: String,
    val porterHubName: String?,
    val franchiseGst: String?,
    val franchisePan: String?,
    val franchiseCanceledCheque: String?,
    val status: FranchiseStatus,
    val teamId: Int?,
    val daysOfOperation: String?,
    val startTime: String,
    val endTime: String,
    val cutOffTime: String,
    val kamUser: String?,
    val hlpEnabled: Boolean,
    val radiusCoverage: BigDecimal,
    val showCrNumber: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isActive: Boolean,
    val daysOfTheWeek: String?,
    val courierPartners: List<String>,
)
