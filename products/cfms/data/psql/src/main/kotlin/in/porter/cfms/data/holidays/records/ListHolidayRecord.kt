package `in`.porter.cfms.data.holidays.records

import java.time.LocalDate

data class ListHolidayRecord(
    val holidayId: Int,
    val franchiseId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val holidayName: String?,
    val leaveType: String?,
    val backupFranchiseIds: String?
)
