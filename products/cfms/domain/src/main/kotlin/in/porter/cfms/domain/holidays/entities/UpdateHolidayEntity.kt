package `in`.porter.cfms.domain.holidays.entities

import java.time.Instant
import java.time.LocalDate

data class UpdateHolidayEntity(
    val id: Long,
    val franchiseId: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val leaveType: LeaveType? = null,
    val backupFranchiseIds: String? = null,
    val holidayName: String? = null,
    val updatedAt: Instant?
)