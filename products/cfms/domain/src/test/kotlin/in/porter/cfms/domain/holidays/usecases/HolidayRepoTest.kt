/*
package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.holidays.factories.HolidayFactory
import `in`.porter.cfms.domain.holidays.factories.ListHolidayFactory
import `in`.porter.cfms.domain.holidays.factories.UpdateHolidayFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HolidayRepoTest {

    private lateinit var holidayRepo: HolidayRepo

    @BeforeEach
    fun setUp() {
        holidayRepo = mockk()
    }

    @Test
    fun `should return holidays for a given franchise`() = runBlocking {
        val franchiseId = "ABC12"
        val holidays = listOf(HolidayFactory.buildHoliday())

        coEvery { holidayRepo.get(franchiseId) } returns holidays

        val result = holidayRepo.get(franchiseId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(franchiseId, result[0].franchiseId)
    }

    @Test
    fun `should return holiday by franchise ID and date`() = runBlocking {
        val franchiseId = "ABC12"
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(1)
        val holiday = UpdateHolidayFactory.build(startDate = startDate, endDate = endDate)

        coEvery { holidayRepo.getByIdAndDate(franchiseId, startDate, endDate) } returns holiday

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(franchiseId, result?.franchiseId)
        assertEquals(startDate, result?.startDate)
        assertEquals(endDate, result?.endDate)
    }

    @Test
    fun `should record a holiday`() = runBlocking {
        val holiday = HolidayFactory.buildHoliday()

        coEvery { holidayRepo.record(holiday) } returns 1

        val result = holidayRepo.record(holiday)

        assertEquals(1, result)
        coVerify(exactly = 1) { holidayRepo.record(holiday) }
    }

    @Test
    fun `should update a holiday`() = runBlocking {
        val updateHolidayEntity = UpdateHolidayFactory.build()

        coEvery { holidayRepo.update(updateHolidayEntity) } returns 1

        assertDoesNotThrow {
            runBlocking {
                holidayRepo.update(updateHolidayEntity)
            }
        }

        coVerify(exactly = 1) { holidayRepo.update(updateHolidayEntity) }
    }

    @Test
    fun `should return holiday by ID`() = runBlocking {
        val holidayId = 1
        val holiday = UpdateHolidayFactory.build(holidayId = holidayId)

        coEvery { holidayRepo.getById(holidayId) } returns holiday

        val result = holidayRepo.getById(holidayId)

        assertNotNull(result)
        assertEquals(holidayId, result?.holidayId)
        coVerify(exactly = 1) { holidayRepo.getById(holidayId) }
    }

    @Test
    fun `should delete holiday by ID`() = runBlocking {
        val holidayId = 1

        coEvery { holidayRepo.deleteById(holidayId) } returns Unit

        assertDoesNotThrow {
            runBlocking {
                holidayRepo.deleteById(holidayId)
            }
        }

        coVerify(exactly = 1) { holidayRepo.deleteById(holidayId) }
    }

@Test
    fun `should return all holidays for a given date`() = runBlocking {
        val date = LocalDate.now()
        val holidays = listOf(HolidayFactory.buildHoliday())

        coEvery { holidayRepo.getAllByDate(date) } returns holidays

        val result = holidayRepo.getAllByDate(date)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(date, result[0].startDate)
    }

    @Test
    fun `should return empty list when no holidays exist for the given franchise`() = runBlocking {
        val franchiseId = "123"

        coEvery { holidayRepo.get(franchiseId) } returns emptyList()

        val result = holidayRepo.get(franchiseId)

        assertNotNull(result)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return null when no holiday exists for the given franchise ID and date`() = runBlocking {
        val franchiseId = "123"
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(1)

        coEvery { holidayRepo.getByIdAndDate(franchiseId, startDate, endDate) } returns null

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNull(result)
    }

    @Test
    fun `should throw exception when holiday already exists and conflicts with unique constraint`() = runBlocking {
        val holiday = HolidayFactory.buildHoliday()

        coEvery { holidayRepo.record(holiday) } throws IllegalStateException("Unique constraint violation")

        val exception = assertThrows<IllegalStateException> {
            runBlocking { holidayRepo.record(holiday) }
        }

        assertEquals("Unique constraint violation", exception.message)
        coVerify(exactly = 1) { holidayRepo.record(holiday) }
    }

    @Test
    fun `should return empty list when no holidays exist for the given date`() = runBlocking {
        val date = LocalDate.now()

        coEvery { holidayRepo.getAllByDate(date) } returns emptyList()

        val result = holidayRepo.getAllByDate(date)

        assertNotNull(result)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should find holidays based on filters`() = runBlocking {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = 1
        val size = 10
        val holidays = listOf(ListHolidayFactory.buildListHoliday())

        coEvery { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) } returns holidays

        val result = holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size)

        assertNotNull(result)
        assertEquals(1, result.size)
        coVerify(exactly = 1) { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) }
    }

    @Test
    fun `should count holidays based on filters`() = runBlocking {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val count = 5

        coEvery { holidayRepo.countHolidays(franchiseId, leaveType, startDate, endDate) } returns count

        val result = holidayRepo.countHolidays(franchiseId, leaveType, startDate, endDate)

        assertEquals(count, result)
        coVerify(exactly = 1) { holidayRepo.countHolidays(franchiseId, leaveType, startDate, endDate) }
    }

    @Test
    fun `should handle null franchiseId and return empty list`() = runBlocking {
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = 1
        val size = 10

        coEvery { holidayRepo.findHolidays(null, leaveType, startDate, endDate, page, size) } returns emptyList()

        val result = holidayRepo.findHolidays(null, leaveType, startDate, endDate, page, size)

        assertNotNull(result)
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { holidayRepo.findHolidays(null, leaveType, startDate, endDate, page, size) }
    }

    @Test
    fun `should return empty list when startDate is after endDate`() = runBlocking {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 12, 31)
        val endDate = LocalDate.of(2024, 1, 1) // End date is before start date
        val page = 1
        val size = 10

        coEvery { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) } returns emptyList()

        val result = holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size)

        assertNotNull(result)
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) }
    }

    @Test
    fun `should return empty list for negative page number`() = runBlocking {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = -1 // Invalid negative page
        val size = 10

        coEvery { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) } returns emptyList()

        val result = holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size)

        assertNotNull(result)
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) }
    }

    @Test
    fun `should return empty list for page size zero`() = runBlocking {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = 1
        val size = 0 // Invalid page size

        coEvery { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) } returns emptyList()

        val result = holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size)

        assertNotNull(result)
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) }
    }

    @Test
    fun `should handle large page size correctly`() = runBlocking {
        val franchiseId = "ABC12"
        val leaveType = null
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 12, 31)
        val page = 1
        val size = Int.MAX_VALUE // Large size

        coEvery { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) } returns emptyList()

        val result = holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size)

        assertNotNull(result)
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { holidayRepo.findHolidays(franchiseId, leaveType, startDate, endDate, page, size) }
    }




}
*/
