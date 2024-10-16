package `in`.porter.cfms.api.holidays.usecases

import `in`.porter.cfms.api.holidays.factories.ListHolidaysResponseTestFactory
import `in`.porter.cfms.api.holidays.factories.ListHolidaysServiceTestFactory
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import `in`.porter.cfms.api.service.holidays.usecases.ListHolidaysService
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.usecases.ListHolidays
import `in`.porter.cfms.domain.holidays.usecases.HolidaySearchResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ListHolidaysServiceTest {

    private lateinit var listHolidays: ListHolidays
    private lateinit var listHolidaysService: ListHolidaysService

    @BeforeEach
    fun setup() {
        listHolidays = mockk()
        listHolidaysService = ListHolidaysService(listHolidays)
    }

    @Test
    fun `should return ListHolidaysResponse on successful listing`() = runBlocking {
        val request = ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val listHoliday = ListHolidaysServiceTestFactory.createListHoliday()
        val holidaysResult = HolidaySearchResult(
            totalRecords = 1,
            data = listOf(listHoliday)
        )

        // Mock the behavior of the domain's listHolidays method
        coEvery { listHolidays.listHolidays(any(), any(), any(), any(), any(), any()) } returns holidaysResult

        // Get the expected response from the factory
        val expectedResponse = ListHolidaysResponseTestFactory.defaultResponse()

        val response = listHolidaysService.listHolidays(request)

        // Verify the actual response against the expected response from the factory
        assertEquals(expectedResponse.totalRecords, response.totalRecords)
        assertEquals(expectedResponse.totalPages, response.totalPages)
        assertEquals(expectedResponse.holidays.size, response.holidays.size)
        assertEquals(expectedResponse.holidays[0].holidayDetails.name, response.holidays[0].holidayDetails.name)
    }

    @Test
    fun `should return empty ListHolidaysResponse when no holidays are found`() = runBlocking {
        val request = ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val holidaysResult = HolidaySearchResult(
            totalRecords = 0,
            data = emptyList<ListHoliday>()
        )

        // Mock the behavior of the domain's listHolidays method
        coEvery { listHolidays.listHolidays(any(), any(), any(), any(), any(), any()) } returns holidaysResult

        val response = listHolidaysService.listHolidays(request)

        // Verify that the response is empty
        assertEquals(0, response.totalRecords)
        assertEquals(0, response.holidays.size)
    }

    @Test
    fun `should handle large number of records correctly`() = runBlocking {
        val request = ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val holidaysResult = HolidaySearchResult(
            totalRecords = 100,
            data = List(10) { ListHolidaysServiceTestFactory.createListHoliday() }
        )

        // Mock the behavior of the domain's listHolidays method
        coEvery { listHolidays.listHolidays(any(), any(), any(), any(), any(), any()) } returns holidaysResult

        val response = listHolidaysService.listHolidays(request)

        assertEquals(100, response.totalRecords)
        assertEquals(10, response.holidays.size)
        assertEquals(10, response.size)
    }

    @Test
    fun `should return ListHolidaysResponse with calculated totalPages`() = runBlocking {
        val request = ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val holidaysResult = HolidaySearchResult(
            totalRecords = 25,
            data = List(10) { ListHolidaysServiceTestFactory.createListHoliday() }
        )

        // Mock the behavior of the domain's listHolidays method
        coEvery { listHolidays.listHolidays(any(), any(), any(), any(), any(), any()) } returns holidaysResult

        val response = listHolidaysService.listHolidays(request)

        assertEquals(3, response.totalPages) // 25 records, 10 per page => 3 pages
    }

    @Test
    fun `should throw exception if domain layer throws`() = runBlocking {
        val request = ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        // Mock the behavior to throw an exception
        coEvery { listHolidays.listHolidays(any(), any(), any(), any(), any(), any()) } throws RuntimeException("Unexpected error")

        val exception = assertThrows(RuntimeException::class.java) {
            runBlocking {
                listHolidaysService.listHolidays(request)
            }
        }

        assertEquals("Unexpected error", exception.message)
    }
}
