package `in`.porter.cfms.data.franchise.records

import java.math.BigDecimal

data class ListFranchisesRecord(
    val franchiseId: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val pocName: String,
    val pocMobile: String,
    val pocEmail: String,
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

