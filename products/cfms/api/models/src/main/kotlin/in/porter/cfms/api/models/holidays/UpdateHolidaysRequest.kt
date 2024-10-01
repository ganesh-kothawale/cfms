package `in`.porter.cfms.api.models.holidays

import java.time.LocalDate

data class UpdateHolidaysRequest(
    val franchiseId: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val holidayName: String?,
    val leaveType: LeaveType?,
    val backupFranchiseIds: String?
)

