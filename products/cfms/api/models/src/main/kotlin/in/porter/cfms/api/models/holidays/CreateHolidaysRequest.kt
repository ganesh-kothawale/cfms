package `in`.porter.cfms.api.models.holidays

import java.time.LocalDate

data class CreateHolidaysRequest(
    val franchise_id: String,  // ID of the franchise
    val start_date: LocalDate,  // Start date of the holiday
    val end_date: LocalDate,  // End date of the holiday
    val holiday_name: String? = null,  //  name of the holiday
    val leave_type: LeaveType,  // Leave type must be "Normal" or "Emergency"
    val backup_franchise_ids: String? = null  // comma-separated list of backup franchise IDs
)
