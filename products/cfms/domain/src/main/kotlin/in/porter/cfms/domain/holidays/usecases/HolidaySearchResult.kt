package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.holidays.entities.ListHoliday

data class HolidaySearchResult(
    val data: List<ListHoliday>,        // List of holidays for the current page
    val totalRecords: Int               // Total number of matching records
)
