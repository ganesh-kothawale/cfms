package `in`.porter.cfms.api.holidays.usecases


import `in`.porter.cfms.api.holidays.factories.CreateHolidaysRequestTestFactory
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.holidays.CreateHolidaysService
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType

import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
//import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateHolidaysServiceTest {

    private lateinit var createHolidaysService: CreateHolidaysService
    private lateinit var holidayRepo: HolidayRepo

    @BeforeEach
    fun setup() {
        holidayRepo = mockk()
        createHolidaysService = CreateHolidaysService(holidayRepo)
    }

    @Test
    fun `should successfully create a holiday`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()

        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { holidayRepo.record(any()) } returns 1L

        val holidayId = createHolidaysService.invoke(request)

        assertEquals(1L, holidayId)

        coVerify(exactly = 1) { holidayRepo.record(match {
            // Verify core fields
            it.franchiseId == request.franchise_id &&
                    it.startDate == request.start_date &&
                    it.endDate == request.end_date &&
                    it.holidayName == request.holiday_name &&
                    it.leaveType == LeaveType.Normal &&
                    it.backupFranchiseIds == request.backup_franchise_ids &&

                    // Verify createdAt and updatedAt are close to the current time
                    it.createdAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true &&
                    it.updatedAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true
        })}
    }

    // Extension function to match timestamps within a certain range
    fun Instant.isCloseTo(other: Instant, duration: Duration): Boolean {
        return !this.isBefore(other.minus(duration)) && !this.isAfter(other.plus(duration))
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
        coVerify(exactly = 0) { holidayRepo.record(any()) }
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
        coVerify(exactly = 0) { holidayRepo.record(any()) }
    }

    @Test
    fun `should throw exception when holiday already exists`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()

        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns mockk()

        val exception = assertThrows<CfmsException> {
            createHolidaysService.invoke(request)
        }

        assertEquals("Holiday is already applied by franchise ID ${request.franchise_id} from ${request.start_date} to ${request.end_date}.", exception.message)
        coVerify(exactly = 0) { holidayRepo.record(any()) }
    }

    @Test
    fun `should throw exception when storing holiday fails`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()

        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { holidayRepo.record(any()) } throws Exception("DB error")

        val exception = assertThrows<CfmsException> {
            createHolidaysService.invoke(request)
        }

        assertEquals("Failed to store holiday in DB: DB error", exception.message)
        coVerify(exactly = 1) { holidayRepo.record(any()) }
    }

    @Test
    fun `should successfully create a holiday with Normal leave type`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(leaveType = `in`. porter. cfms. api. models. holidays. LeaveType.Normal)

        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { holidayRepo.record(any()) } returns 1L

        val holidayId = createHolidaysService.invoke(request)

        assertEquals(1L, holidayId)

        coVerify(exactly = 1) { holidayRepo.record(match {
            it.franchiseId == request.franchise_id &&
                    it.startDate == request.start_date &&
                    it.endDate == request.end_date &&
                    it.holidayName == request.holiday_name &&
                    it.leaveType == LeaveType.Normal &&
                    it.backupFranchiseIds == request.backup_franchise_ids &&
                    it.createdAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true &&
                    it.updatedAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true
        })}
    }

    @Test
    fun `should successfully create a holiday with Emergency leave type`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(leaveType = `in`. porter. cfms. api. models. holidays. LeaveType.Emergency)

        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { holidayRepo.record(any()) } returns 1L

        val holidayId = createHolidaysService.invoke(request)

        assertEquals(1L, holidayId)

        coVerify(exactly = 1) { holidayRepo.record(match {
            it.franchiseId == request.franchise_id &&
                    it.startDate == request.start_date &&
                    it.endDate == request.end_date &&
                    it.holidayName == request.holiday_name &&
                    it.leaveType == LeaveType.Emergency &&
                    it.backupFranchiseIds == request.backup_franchise_ids &&
                    it.createdAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true &&
                    it.updatedAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true
        })}
    }

    @Test
    fun `should return null for non-existing holiday`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()

        // Simulate no existing holiday in the repo (null case)
        coEvery { holidayRepo.getByIdAndDate(request.franchise_id, request.start_date, request.end_date) } returns null

        // Ensure holiday gets created
        coEvery { holidayRepo.record(any()) } returns 1L

        val holidayId = createHolidaysService.invoke(request)

        assertEquals(1L, holidayId)
        coVerify(exactly = 1) { holidayRepo.record(any()) }
    }

    @Test
    fun `should return holiday when holiday already exists`() = runBlocking {
        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()

        // Simulate existing holiday in the repo
        val existingHoliday = Holiday(
            franchiseId = request.franchise_id,
            startDate = request.start_date,
            endDate = request.end_date,
            holidayName = request.holiday_name,
            leaveType = LeaveType.valueOf(request.leave_type.name),
            backupFranchiseIds = request.backup_franchise_ids,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        coEvery { holidayRepo.getByIdAndDate(request.franchise_id, request.start_date, request.end_date) } returns existingHoliday

        // Check that exception is thrown for an existing holiday
        val exception = assertThrows<CfmsException> {
            createHolidaysService.invoke(request)
        }

        assertEquals(
            "Holiday is already applied by franchise ID ${request.franchise_id} from ${request.start_date} to ${request.end_date}.",
            exception.message
        )
        coVerify(exactly = 0) { holidayRepo.record(any()) }
    }


}
