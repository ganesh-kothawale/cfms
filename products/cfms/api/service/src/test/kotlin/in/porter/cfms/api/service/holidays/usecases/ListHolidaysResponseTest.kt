/*
package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.service.holidays.factories.ListHolidaysResponseTestFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ListHolidaysResponseTest {

    @Test
    fun `should create ListHolidaysResponse with correct values`() {
        val response = ListHolidaysResponseTestFactory.defaultResponse()
        assertEquals(1, response.page)
        assertEquals(10, response.size)
        assertEquals(1, response.totalPages)
        assertEquals(1, response.totalRecords)
        assertEquals(1, response.holidays.size)

        val holiday = response.holidays[0]
        assertEquals(1, holiday.holidayId)
        assertEquals("franchise-123", holiday.franchiseId)
        assertEquals("New Year Holiday", holiday.holidayDetails.name)
        assertEquals("Normal", holiday.holidayDetails.leaveType)
    }
}*/
