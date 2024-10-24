/*
package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.factories.HolidayFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class CreateHolidayTest {

    private lateinit var mockHolidayRepo: HolidayRepo
    private lateinit var mockCourierService: CourierApplyLeaveCallingService
    private lateinit var recordHoliday: CreateHoliday

    @BeforeEach
    fun setup() {
        mockHolidayRepo = mockk()
        mockCourierService = mockk()
        recordHoliday = CreateHoliday(mockHolidayRepo, mockCourierService)
    }

    @Test
    fun `should record holiday successfully`() = runBlocking {
        // Mocking successful response from external service and repo
        coEvery { mockHolidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { mockHolidayRepo.record(any()) } returns 1
        coEvery { mockCourierService.applyLeave(any()) } returns ApplyLeaveResponse("Leave applied successfully")

        // Using factory to build holiday object
        val holiday = HolidayFactory.buildHoliday(
            franchiseId = "ABC12",
            startDate = LocalDate.parse("2024-12-20"),
            endDate = LocalDate.parse("2024-12-21")
        )

        val holidayId = recordHoliday.createHoliday(holiday)

        assertEquals(1, holidayId)
        coVerify { mockHolidayRepo.record(holiday) }
    }

    @Test
    fun `should fail to record holiday when leave application fails`() = runBlocking {
        // Mocking failed response from external service
        coEvery { mockHolidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { mockHolidayRepo.record(any()) } returns 1
        coEvery { mockCourierService.applyLeave(any()) } throws CfmsException("API call failed")

        // Using factory to build holiday object
        val holiday = HolidayFactory.buildHoliday(
            franchiseId = "ABC12",
            startDate = LocalDate.parse("2024-12-20"),
            endDate = LocalDate.parse("2024-12-21")
        )

        val exception = assertThrows<CfmsException> {
            recordHoliday.createHoliday(holiday)
        }

        assertEquals("Failed to apply leave: API call failed", exception.message)
    }
}
*/
