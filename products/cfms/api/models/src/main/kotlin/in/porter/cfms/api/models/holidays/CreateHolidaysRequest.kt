package `in`.porter.cfms.api.models.holidays

import java.time.LocalDate

data class CreateHolidaysRequest(
    val franchiseId: String,  // ID of the franchise
    val startDate: LocalDate,  // Start date of the holiday
    val endDate: LocalDate,  // End date of the holiday
    val holidayName: String?,  //  name of the holiday
    val leaveType: LeaveType,  // Leave type must be "Normal" or "Emergency"
    val backupFranchiseIds: String?  // comma-separated list of backup franchise IDs
)
