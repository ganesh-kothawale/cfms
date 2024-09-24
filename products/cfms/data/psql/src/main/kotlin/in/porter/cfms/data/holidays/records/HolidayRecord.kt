package `in`.porter.cfms.data.holidays.records

import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.LocalDate
import java.time.Instant

data class HolidayRecord(
    val franchiseId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val holidayName: String?,
    val leaveType: LeaveType,  // This will map to the enum value in string form
    val backupFranchiseIds: String?,  // Comma-separated list of franchise IDs
    val createdAt: Instant,  // Timestamp when the record was created
    val updatedAt: Instant   // Timestamp when the record was last updated
)