package `in`.porter.cfms.domain.holidays.entities

import java.time.Instant
import java.time.LocalDate

data class UpdateHolidayEntity(
    val id: Int?,
    val franchiseId: String? = null,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val leaveType: LeaveType,
    val backupFranchiseIds: String? = null,
    val holidayName: String? = null,
    val updatedAt: Instant? = null,
    val createdAt: Instant? = null
)