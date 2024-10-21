package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.service.holidays.factories.ListHolidaysRequestTestFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ListHolidaysRequestTest {

    @Test
    fun `should create ListHolidaysRequest with correct values`() {
        val request = ListHolidaysRequestTestFactory.defaultRequest()
        assertEquals(1, request.page)
        assertEquals(10, request.size)
        assertEquals("franchise-123", request.franchiseId)
        assertEquals("Normal", request.leaveType)
        assertEquals(LocalDate.of(2024, 1, 1), request.startDate)
        assertEquals(LocalDate.of(2024, 12, 31), request.endDate)
    }
}