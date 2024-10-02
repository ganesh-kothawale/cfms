package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.usecases.DeleteHolidayDomainService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteHolidaysServiceTest {

    private lateinit var deleteHolidayDomainService: DeleteHolidayDomainService
    private lateinit var deleteHolidaysService: DeleteHolidaysService

    @BeforeEach
    fun setup() {
        deleteHolidayDomainService = mockk()
        deleteHolidaysService = DeleteHolidaysService(deleteHolidayDomainService)
    }

    @Test
    fun `should successfully delete holiday`() = runBlocking {
        val holidayId = 1

        // Mock the domain service to do nothing when deleteHoliday is called
        coEvery { deleteHolidayDomainService.deleteHoliday(holidayId) } returns Unit

        // Invoke the service
        deleteHolidaysService.deleteHoliday(holidayId)

        // Verify the domain service method is called exactly once
        coVerify(exactly = 1) { deleteHolidayDomainService.deleteHoliday(holidayId) }
    }

    @Test
    fun `should throw CfmsException when domain throws CfmsException`() = runBlocking {
        val holidayId = 1
        val exceptionMessage = "Holiday not found with ID $holidayId"

        // Mock the domain service to throw CfmsException
        coEvery { deleteHolidayDomainService.deleteHoliday(holidayId) } throws CfmsException(exceptionMessage)

        // Assert that the CfmsException is thrown
        val exception = assertThrows(CfmsException::class.java) {
            runBlocking { deleteHolidaysService.deleteHoliday(holidayId) }
        }

        assertEquals("Unexpected error occurred while deleting holiday: $exceptionMessage", exception.message)
        coVerify(exactly = 1) { deleteHolidayDomainService.deleteHoliday(holidayId) }
    }

    @Test
    fun `should throw general Exception for unexpected errors`() = runBlocking {
        val holidayId = 1
        val exceptionMessage = "Unexpected error occurred"

        // Mock the domain service to throw a general exception
        coEvery { deleteHolidayDomainService.deleteHoliday(holidayId) } throws Exception(exceptionMessage)

        // Assert that the general exception is propagated
        val exception = assertThrows(Exception::class.java) {
            runBlocking { deleteHolidaysService.deleteHoliday(holidayId) }
        }

        assertEquals("Unexpected error occurred while deleting holiday: $exceptionMessage", exception.message)
        coVerify(exactly = 1) { deleteHolidayDomainService.deleteHoliday(holidayId) }
    }
}
