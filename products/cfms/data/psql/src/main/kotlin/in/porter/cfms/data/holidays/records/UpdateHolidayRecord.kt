package `in`.porter.cfms.data.holidays.records

import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import java.time.LocalDate

data class UpdateHolidayRecord(
    val holidayId: Int,
    val franchiseId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val holidayName: String?,
    val leaveType: LeaveType,  // Assume leaveType is a String representation in the DB
    val backupFranchiseIds: String?,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
