package `in`.porter.cfms.api.models.holidays

import java.time.LocalDate

data class UpdateHolidaysRequest(
    val holidayId: String,
    val franchiseId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val holidayName: String?,
    val leaveType: LeaveType,
    val backupFranchiseIds: List<String>
)

