package `in`.porter.cfms.api.models.holidays

import java.time.LocalDate

data class ListHolidaysRequest(
    val page: Int,
    val size: Int,
    val franchiseId: String?,
    val leaveType: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?
)