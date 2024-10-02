package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.factories.HolidayFactory
import `in`.porter.cfms.domain.holidays.factories.UpdateHolidayEntityTestFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateHolidayTest {

    private lateinit var holidayRepo: HolidayRepo
    private lateinit var courierService: CourierApplyLeaveCallingService
    private lateinit var updateHoliday: UpdateHoliday

    @BeforeEach
    fun setUp() {
        holidayRepo = mockk()
        courierService = mockk()
        updateHoliday = UpdateHoliday(holidayRepo, courierService)
    }

    @Test
    fun `should successfully update holiday when valid data is provided`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayEntityTestFactory.build()
        coEvery { holidayRepo.getById(updateHolidayEntity.id) } returns updateHolidayEntity
        coEvery { courierService.applyLeave(any()) } returns CourierApplyLeaveCallingService.ApplyLeaveResponse("Leave applied successfully")
        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { holidayRepo.update(updateHolidayEntity) } returns Unit

        // Act
        updateHoliday.updateHoliday(updateHolidayEntity)

        // Assert
        coVerify(exactly = 1) { holidayRepo.update(updateHolidayEntity) }
        coVerify(exactly = 1) { courierService.applyLeave(any()) }
    }

    @Test
    fun `should throw exception when holiday with given ID is not found`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayEntityTestFactory.build(id = 1)
        coEvery { holidayRepo.getById(updateHolidayEntity.id) } returns null

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Holiday with ID 1 not found.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.id) }
    }

    @Test
    fun `should throw exception when trying to update franchise ID`() = runBlocking {
        // Arrange
        val existingHoliday = UpdateHolidayEntityTestFactory.build(franchiseId = "ABC12")
        val updateHolidayEntity = UpdateHolidayEntityTestFactory.build(franchiseId = "XYZ34")
        coEvery { holidayRepo.getById(updateHolidayEntity.id) } returns existingHoliday

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Franchise ID cannot be updated.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.id) }
    }

    @Test
    fun `should throw exception if the holiday's end date has passed`() = runBlocking {
        // Arrange
        val existingHoliday = UpdateHolidayEntityTestFactory.build(endDate = LocalDate.now().minusDays(1))
        coEvery { holidayRepo.getById(existingHoliday.id) } returns existingHoliday

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(existingHoliday) }
        }

        assertEquals("Cannot update a holiday whose end date has already passed.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(existingHoliday.id) }
    }

    @Test
    fun `should throw exception when external apply leave API fails`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayEntityTestFactory.build()
        coEvery { holidayRepo.getById(updateHolidayEntity.id) } returns updateHolidayEntity
        coEvery { courierService.applyLeave(any()) } throws CfmsException("Failed to apply leave")
        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Failed to apply leave: Failed to apply leave", exception.message)
        coVerify(exactly = 1) { courierService.applyLeave(any()) }
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.id) }
    }

    @Test
    fun `should throw exception if holiday already exists for given dates`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayEntityTestFactory.build()
        val existingHoliday = HolidayFactory.buildHoliday()  // Ensure this returns a `Holiday` type, not `UpdateHolidayEntity`
        val applyLeaveResponse = ApplyLeaveResponse(message = "Leave applied successfully")

        coEvery { holidayRepo.getById(updateHolidayEntity.id) } returns updateHolidayEntity
        coEvery { holidayRepo.getByIdAndDate(updateHolidayEntity.franchiseId!!, updateHolidayEntity.startDate, updateHolidayEntity.endDate) } returns existingHoliday
        coEvery { courierService.applyLeave(any()) } returns applyLeaveResponse

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Holiday is already applied for the given dates.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.id) }
        coVerify(exactly = 1) { holidayRepo.getByIdAndDate(updateHolidayEntity.franchiseId!!, updateHolidayEntity.startDate, updateHolidayEntity.endDate) }
    }


}
