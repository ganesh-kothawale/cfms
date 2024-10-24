package `in`.porter.cfms.domain.holidays.entities

import java.time.LocalDate

data class ListHoliday(
    val holidayId: String,
    val franchiseId: String,
    val holidayPeriod: HolidayPeriod,            // Nested class for holiday period
    val holidayDetails: HolidayDetails,          // Nested class for holiday details
    val franchise: ListHolidaysFranchise         // Franchise details
)

data class HolidayPeriod(
    val fromDate: LocalDate,                     // Start date of the holiday
    val toDate: LocalDate                        // End date of the holiday
)

data class HolidayDetails(
    val name: String?,                           // Name of the holiday
    val leaveType: String?,                      // Leave type as String (can be validated as enum before DB operations)
    val backupFranchise: String?                 // Backup franchise ID if present
)
