/*
package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.factories.HolidaySearchResultTestFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ListHolidaysTest {

    private lateinit var holidayRepo: HolidayRepo
    private lateinit var listHolidays: ListHolidays

    @BeforeEach
    fun setup() {
        holidayRepo = mockk()
        listHolidays = ListHolidays(holidayRepo)
    }

    @Test
    fun `should return HolidaySearchResult when holidays are found`() = runBlocking {
        val holidaySearchResult = HolidaySearchResultTestFactory.createHolidaySearchResult(1)

        // Mock the repository behavior
        coEvery { holidayRepo.findHolidays(any(), any(), any(), any(), any(), any()) } returns holidaySearchResult.data
        coEvery { holidayRepo.countHolidays(any(), any(), any(), any()) } returns holidaySearchResult.totalRecords

        val result = listHolidays.listHolidays(
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31),
            page = 1,
            size = 10
        )

        // Verify the result
        assertEquals(holidaySearchResult.totalRecords, result.totalRecords)
        assertEquals(holidaySearchResult.data.size, result.data.size)
        assertEquals("New Year Holiday", result.data[0].holidayDetails.name)
    }

    @Test
    fun `should return empty HolidaySearchResult when no holidays are found`() = runBlocking {
        val holidaySearchResult = HolidaySearchResultTestFactory.createHolidaySearchResult(0)

        // Mock the repository behavior
        coEvery { holidayRepo.findHolidays(any(), any(), any(), any(), any(), any()) } returns holidaySearchResult.data
        coEvery { holidayRepo.countHolidays(any(), any(), any(), any()) } returns holidaySearchResult.totalRecords

        val result = listHolidays.listHolidays(
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31),
            page = 1,
            size = 10
        )

        // Verify the result
        assertEquals(0, result.totalRecords)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun `should throw CfmsException for invalid leave type`() = runBlocking {
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking {
                listHolidays.listHolidays(
                    franchiseId = "franchise-123",
                    leaveType = "InvalidLeaveType", // Invalid leave type
                    startDate = LocalDate.of(2024, 1, 1),
                    endDate = LocalDate.of(2024, 12, 31),
                    page = 1,
                    size = 10
                )
            }
        }

        assertEquals("Invalid leaveType value: InvalidLeaveType", exception.message)
    }

    @Test
    fun `should handle null leave type gracefully`() = runBlocking {
        val holidaySearchResult = HolidaySearchResultTestFactory.createHolidaySearchResult(1)

        // Mock the repository behavior
        coEvery { holidayRepo.findHolidays(any(), isNull(), any(), any(), any(), any()) } returns holidaySearchResult.data
        coEvery { holidayRepo.countHolidays(any(), isNull(), any(), any()) } returns holidaySearchResult.totalRecords

        val result = listHolidays.listHolidays(
            franchiseId = "franchise-123",
            leaveType = null, // Null leave type
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31),
            page = 1,
            size = 10
        )

        // Verify the result
        assertEquals(holidaySearchResult.totalRecords, result.totalRecords)
        assertEquals(holidaySearchResult.data.size, result.data.size)
    }

    @Test
    fun `should throw exception for negative page number`() {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = -1 // Invalid negative page
        val size = 10

        val exception = assertThrows(CfmsException::class.java) {
            runBlocking {
                listHolidays.listHolidays(franchiseId, leaveType, startDate, endDate, page, size)
            }
        }

        assertEquals("Page number must be greater than zero.", exception.message)
    }

    @Test
    fun `should throw exception for page size zero`() {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = 1
        val size = 0 // Invalid page size

        val exception = assertThrows(CfmsException::class.java) {
            runBlocking {
                listHolidays.listHolidays(franchiseId, leaveType, startDate, endDate, page, size)
            }
        }

        assertEquals("Page size must be greater than zero.", exception.message)
    }
}
*/
