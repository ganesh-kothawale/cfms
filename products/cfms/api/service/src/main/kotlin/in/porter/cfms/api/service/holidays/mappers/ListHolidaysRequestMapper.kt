package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.holidays.LeaveType
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import java.time.LocalDate
import javax.inject.Inject

class ListHolidaysRequestMapper
@Inject
constructor() {
    fun toDomain(
        page: Int,
        size: Int,
        franchiseId: String?,
        leaveType: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): ListHolidaysRequest {
        return ListHolidaysRequest(
            page = page,
            size = size,
            franchiseId = franchiseId,
            leaveType = leaveType, // Convert leaveType string to enum
            startDate = startDate,   // Parse startDate string to LocalDate
            endDate = endDate        // Parse endDate string to LocalDate
        )
    }
}

