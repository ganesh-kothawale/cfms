package `in`.porter.cfms.api.models.holidays

import java.time.LocalDate

data class ListHolidaysResponse(
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalRecords: Int,
    val holidays: List<HolidayResponse>
)

data class HolidayResponse(
    val holidayId: String,
    val franchiseId: String,
    val holidayPeriod: HolidayPeriod,
    val holidayDetails: HolidayDetails,
    val franchise: FranchiseResponse
)

data class HolidayPeriod(
    val fromDate: LocalDate,
    val toDate: LocalDate
)

data class HolidayDetails(
    val name: String?,
    val leaveType: String,
    val backupFranchise: String?
)

data class FranchiseResponse(
    val franchiseId: String,
    val franchiseName: String,
    val poc: FranchisePoc,
    val address: FranchiseAddress
)

data class FranchisePoc(
    val name: String,
    val contact: String
)

data class FranchiseAddress(
    val gpsAddress: String,
    val city: String,
    val state: String
)
