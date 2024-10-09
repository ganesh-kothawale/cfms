package `in`.porter.cfms.domain.holidays.entities

import java.time.LocalDate
import java.time.Instant

data class Holiday (
    val franchiseId: String,  // Franchise ID for which the holiday is being created
    val startDate: LocalDate,  // Start date of the holiday
    val endDate: LocalDate,  // End date of the holiday
    val leaveType: LeaveType,  // Type of leave (Normal or Emergency)
    val backupFranchiseIds: String? = null,  // List of backup franchise IDs (optional)
    val holidayName: String? = null,
    val createdAt: Instant?,
    val updatedAt: Instant?
)