package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.holidays.factories.HolidayFactory
import `in`.porter.cfms.domain.holidays.factories.UpdateHolidayEntityTestFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HolidayRepoTest {

    private lateinit var holidayRepo: HolidayRepo

    @BeforeEach
    fun setUp() {
        holidayRepo = mockk()
    }


    @Test
    fun `should return holiday by franchise ID and date`() = runBlocking {
        val franchiseId = "ABC12"
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
    fun `should update a holiday`() = runBlocking {
        val updateHolidayEntity = UpdateHolidayEntityTestFactory.build()

        coEvery { holidayRepo.update(updateHolidayEntity) } returns Unit

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
        val holiday = UpdateHolidayEntityTestFactory.build(id = holidayId)

        coEvery { holidayRepo.getById(holidayId) } returns holiday

        val result = holidayRepo.getById(holidayId)

        assertNotNull(result)
        assertEquals(holidayId, result?.id)
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

}
