package `in`.porter.cfms.api.models.franchises

import java.math.BigDecimal

data class FranchiseeResponse(
    val franchiseId: String,
    val address: AddressResponse,
    val poc: PointOfContactResponse,
    val radiusCovered: BigDecimal,
    val hlpEnabled: Boolean,
    val isActive: String,
    val daysOfTheWeek: String,
    val cutOffTime: String,
    val startTime: String,
    val endTime: String,
    val holidays: List<String>,
    val porterHubName: String,
    val franchiseGst: String,
    val franchisePan: String,
    val franchiseCanceledCheque: String,
    val courierPartners: List<String>,
    val kamUser: String,
    val createdAt: String,
    val updatedAt: String
)
