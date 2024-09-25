package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.factories.HolidayFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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
        val franchiseId = "123"
        val holidays = listOf(HolidayFactory.buildHoliday())

        coEvery { holidayRepo.get(franchiseId) } returns holidays

        val result = holidayRepo.get(franchiseId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(franchiseId, result[0].franchiseId)
    }

    @Test
    fun `should return holiday by franchise ID and date`() = runBlocking {
        val franchiseId = "123"
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(1)
        val holiday = HolidayFactory.buildHoliday(startDate = startDate, endDate = endDate)

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

        coEvery { holidayRepo.record(holiday) } returns 1L

        val result = holidayRepo.record(holiday)

        assertEquals(1L, result)
        coVerify(exactly = 1) { holidayRepo.record(holiday) }
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
}
