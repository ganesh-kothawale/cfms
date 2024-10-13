package `in`.porter.cfms.api.holidays.usecases

import `in`.porter.cfms.api.service.holidays.mappers.ListHolidaysRequestMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ListHolidaysRequestMapperTest {

    private val mapper = ListHolidaysRequestMapper()

    @Test
    fun `should map parameters to ListHolidaysRequest`() {
        val page = 1
        val size = 10
        val franchiseId = "franchise-123"
        val leaveType = "Normal"
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)

        val result = mapper.toDomain(page, size, franchiseId, leaveType, startDate, endDate)

        assertEquals(page, result.page)
        assertEquals(size, result.size)
        assertEquals(franchiseId, result.franchiseId)
        assertEquals(leaveType, result.leaveType)
        assertEquals(startDate, result.startDate)
        assertEquals(endDate, result.endDate)
    }
}
