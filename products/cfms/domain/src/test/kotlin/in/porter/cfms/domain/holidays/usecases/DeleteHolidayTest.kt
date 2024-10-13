package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveRequest
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
class DeleteHolidayTest {

    private lateinit var holidayRepo: HolidayRepo
    private lateinit var courierService: CourierApplyLeaveCallingService
    private lateinit var deleteHolidayDomainService: DeleteHoliday

    @BeforeEach
    fun setup() {
        holidayRepo = mockk()
        courierService = mockk()
        deleteHolidayDomainService = DeleteHoliday(holidayRepo, courierService)
    }

    @Test
    fun `should successfully delete holiday`() = runBlocking {
        val holidayId = 1
        val holiday = UpdateHoliday(
            holidayId = holidayId,
            franchiseId = "ABC12",
            startDate = LocalDate.now().plusDays(1),
            endDate = LocalDate.now().plusDays(5),
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "123,456"
        )

        // Mock fetching the holiday from the repository
        coEvery { holidayRepo.getById(holidayId) } returns holiday

        // Mock the external API call for leave cancellation
        coEvery { courierService.applyLeave(any()) } returns ApplyLeaveResponse("Leave cancelled successfully")

        // Mock the deletion from the repository
        coEvery { holidayRepo.deleteById(holidayId) } returns Unit

        // Call the delete method
        deleteHolidayDomainService.deleteHoliday(holidayId)

        // Verify the interactions
        coVerify(exactly = 1) { holidayRepo.getById(holidayId) }
        coVerify(exactly = 1) { courierService.applyLeave(any<ApplyLeaveRequest>()) }
        coVerify(exactly = 1) { holidayRepo.deleteById(holidayId) }
    }

    @Test
    fun `should throw CfmsException if holiday not found`() = runBlocking {
        val holidayId = 1

        // Mock the holiday not found in the repository
        coEvery { holidayRepo.getById(holidayId) } returns null

        // Assert that CfmsException is thrown
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { deleteHolidayDomainService.deleteHoliday(holidayId) }
        }

        assertEquals("Holiday not found with ID $holidayId", exception.message)

        // Verify that the repo was called once
        coVerify(exactly = 1) { holidayRepo.getById(holidayId) }
    }

    @Test
    fun `should throw CfmsException if holiday cannot be deleted after the start date`() = runBlocking {
        val holidayId = 1
        val holiday = UpdateHoliday(
            holidayId = holidayId,
            franchiseId = "ABC12",
            startDate = LocalDate.now().minusDays(1), // Holiday has already started
            endDate = LocalDate.now().plusDays(5),
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "123,456"
        )

        // Mock fetching the holiday from the repository
        coEvery { holidayRepo.getById(holidayId) } returns holiday

        // Assert that CfmsException is thrown for the invalid date
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { deleteHolidayDomainService.deleteHoliday(holidayId) }
        }

        assertEquals("Holiday cannot be deleted after the allowed time window", exception.message)

        // Verify that no delete or external API call was made
        coVerify(exactly = 1) { holidayRepo.getById(holidayId) }
        coVerify(exactly = 0) { courierService.applyLeave(any()) }
        coVerify(exactly = 0) { holidayRepo.deleteById(holidayId) }
    }

    @Test
    fun `should throw CfmsException if external service fails`() = runBlocking {
        val holidayId = 1
        val holiday = UpdateHoliday(
            holidayId = holidayId,
            franchiseId = "ABC12",
            startDate = LocalDate.now().plusDays(1),
            endDate = LocalDate.now().plusDays(5),
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "123,456"
        )

        // Mock fetching the holiday from the repository
        coEvery { holidayRepo.getById(holidayId) } returns holiday

        // Mock the external API throwing CfmsException
        coEvery { courierService.applyLeave(any()) } throws CfmsException("External API failure")

        // Assert that CfmsException is propagated
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { deleteHolidayDomainService.deleteHoliday(holidayId) }
        }

        assertEquals("Failed to apply leave: External API failure", exception.message)

        // Verify that external API was called and the delete was not
        coVerify(exactly = 1) { courierService.applyLeave(any()) }
        coVerify(exactly = 0) { holidayRepo.deleteById(holidayId) }
    }

    @Test
    fun `should throw Exception for unexpected errors`() = runBlocking {
        val holidayId = 1
        val holiday = UpdateHoliday(
            holidayId = holidayId,
            franchiseId = "ABC123",
            startDate = LocalDate.now().plusDays(1),
            endDate = LocalDate.now().plusDays(5),
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "123,456"
        )

        // Mock fetching the holiday from the repository
        coEvery { holidayRepo.getById(holidayId) } returns holiday

        // Mock the external API throwing a general exception
        coEvery { courierService.applyLeave(any()) } throws Exception("Unexpected API error")

        // Assert that the general exception is propagated
        val exception = assertThrows(Exception::class.java) {
            runBlocking { deleteHolidayDomainService.deleteHoliday(holidayId) }
        }

        assertEquals("Unexpected error occurred while deleting the holiday.", exception.message)

        // Verify that external API was called and deleteById was not called
        coVerify(exactly = 1) { courierService.applyLeave(any()) }
        coVerify(exactly = 0) { holidayRepo.deleteById(holidayId) } // Ensures delete is not called
    }

}
