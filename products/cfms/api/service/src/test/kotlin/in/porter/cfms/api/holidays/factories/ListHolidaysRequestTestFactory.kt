package `in`.porter.cfms.api.holidays.factories

import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import java.time.LocalDate

object ListHolidaysRequestTestFactory {
    fun defaultRequest(): ListHolidaysRequest {
        return ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )
    }
}