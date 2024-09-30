package `in`.porter.cfms.api.holidays.usecases

import `in`.porter.cfms.api.holidays.factories.CreateHolidaysRequestTestFactory
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
import `in`.porter.cfms.api.service.holidays.usecases.CreateHolidaysService
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.usecases.RecordHoliday
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class   CreateHolidaysServiceTest {

    private lateinit var createHolidaysService: CreateHolidaysService
    private lateinit var createHolidaysRequestMapper: CreateHolidaysRequestMapper
    private lateinit var recordHoliday: RecordHoliday

    @BeforeEach
    fun setup() {
        createHolidaysRequestMapper = mockk()
        recordHoliday = mockk()
        createHolidaysService = CreateHolidaysService(createHolidaysRequestMapper, recordHoliday)
    }

    @Test
    fun `should successfully create a holiday`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
            holidayName = null,  // Now nullable
            backupFranchiseIds = null  // Now nullable
        )

        val domainHoliday = mockk<Holiday>()
        coEvery { createHolidaysRequestMapper.toDomain(any()) } returns domainHoliday
        coEvery { recordHoliday.invoke(any()) } returns 1L

        val holidayId = createHolidaysService.invoke(request)

        assertEquals(1L, holidayId)

        coVerify(exactly = 1) { createHolidaysRequestMapper.toDomain(request) }
        coVerify(exactly = 1) { recordHoliday.invoke(domainHoliday) }
    }

    @Test
    fun `should throw exception when start date is before today`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
            startDate = LocalDate.now().minusDays(1),
            endDate = LocalDate.now()
        )

        val exception = assertThrows<CfmsException> {
            createHolidaysService.invoke(request)
        }

        assertEquals("Start date cannot be before today's date.", exception.message)
        coVerify(exactly = 0) { recordHoliday.invoke(any()) }
    }

    @Test
    fun `should throw exception when end date is before start date`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
            startDate = LocalDate.now().plusDays(2),
            endDate = LocalDate.now().plusDays(1)
        )

        val exception = assertThrows<CfmsException> {
            createHolidaysService.invoke(request)
        }

        assertEquals("End date cannot be before start date.", exception.message)
        coVerify(exactly = 0) { recordHoliday.invoke(any()) }
    }

    @Test
    fun `should throw exception when storing holiday fails`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()

        coEvery { createHolidaysRequestMapper.toDomain(any()) } returns mockk()
        coEvery { recordHoliday.invoke(any()) } throws Exception("DB error")

        val exception = assertThrows<CfmsException> {
            createHolidaysService.invoke(request)
        }

        assertEquals("Failed to store holiday in DB: DB error", exception.message)  // Ensure message matches
        coVerify(exactly = 1) { recordHoliday.invoke(any()) }
    }

}
