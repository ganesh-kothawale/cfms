package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.factories.HolidayFactory
import `in`.porter.cfms.domain.holidays.factories.UpdateHolidayFactory
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import io.mockk.MockKException
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
        updateHoliday = mockk()
        updateHoliday = UpdateHoliday(holidayRepo, courierService)
    }

    @Test
    fun `should successfully update holiday when valid data is provided`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayFactory.build()
        coEvery { holidayRepo.getById(updateHolidayEntity.holidayId) } returns updateHolidayEntity
        coEvery { courierService.applyLeave(any()) } returns CourierApplyLeaveCallingService.ApplyLeaveResponse("Leave applied successfully")
        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null
        coEvery { holidayRepo.update(updateHolidayEntity) } returns 1

        // Act
        updateHoliday.updateHoliday(updateHolidayEntity)

        // Assert
        coVerify(exactly = 1) { holidayRepo.update(updateHolidayEntity) }
        coVerify(exactly = 1) { courierService.applyLeave(any()) }
    }

    @Test
    fun `should throw exception when holiday with given ID is not found`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayFactory.build(holidayId = 1)
        coEvery { holidayRepo.getById(updateHolidayEntity.holidayId) } returns null

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Holiday with ID 1 not found.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.holidayId) }
    }

    @Test
    fun `should throw exception when trying to update franchise ID`() = runBlocking {
        // Arrange
        val existingHoliday = UpdateHolidayFactory.build(franchiseId = "ABC12")
        val updateHolidayEntity = UpdateHolidayFactory.build(franchiseId = "XYZ34")
        coEvery { holidayRepo.getById(updateHolidayEntity.holidayId) } returns existingHoliday

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Franchise ID cannot be updated.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.holidayId) }
    }

    @Test
    fun `should throw exception if the holiday's end date has passed`() = runBlocking {
        // Arrange
        val existingHoliday = UpdateHolidayFactory.build(endDate = LocalDate.now().minusDays(1))
        coEvery { holidayRepo.getById(existingHoliday.holidayId) } returns existingHoliday

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(existingHoliday) }
        }

        assertEquals("Cannot update a holiday whose end date has already passed.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(existingHoliday.holidayId) }
    }

    @Test
    fun `should throw exception when external apply leave API fails`() = runBlocking {
        // Arrange
        val updateHolidayEntity = UpdateHolidayFactory.build()
        coEvery { holidayRepo.getById(updateHolidayEntity.holidayId) } returns updateHolidayEntity
        coEvery { courierService.applyLeave(any()) } throws CfmsException("Failed to apply leave")
        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns null

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Failed to apply leave: Failed to apply leave", exception.message)
        coVerify(exactly = 1) { courierService.applyLeave(any()) }
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.holidayId) }
    }
    @Test
    fun `should throw exception when trying to update a holiday that overlaps with an existing holiday`() = runBlocking {
        // Arrange
        val existingHoliday = UpdateHolidayFactory.build()
        val updateHolidayEntity = UpdateHolidayFactory.build(
            holidayId = existingHoliday.holidayId,
            startDate = existingHoliday.startDate.plusDays(1),
            endDate = existingHoliday.endDate.plusDays(1)
        )
        coEvery { holidayRepo.getById(updateHolidayEntity.holidayId) } returns existingHoliday
        coEvery { holidayRepo.getByIdAndDate(any(), any(), any()) } returns UpdateHolidayFactory.build()

        coEvery { courierService.applyLeave(any()) } returns ApplyLeaveResponse("Leave applied successfully")
        coEvery { holidayRepo.update(updateHolidayEntity) } throws CfmsException("Holiday is already applied for the given dates.")
        // No need to mock `updateHoliday.updateHoliday(updateHolidayEntity)` since it's the method under test

        // Act & Assert
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { updateHoliday.updateHoliday(updateHolidayEntity) }
        }

        assertEquals("Holiday is already applied for the given dates.", exception.message)
        coVerify(exactly = 1) { holidayRepo.getById(updateHolidayEntity.holidayId) }
        coVerify(exactly = 1) { holidayRepo.getByIdAndDate(updateHolidayEntity.franchiseId, updateHolidayEntity.startDate, updateHolidayEntity.endDate) }
    }

}
