/*
package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.service.holidays.factories.UpdateHolidaysRequestTestFactory
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.holidays.mappers.UpdateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.usecases.UpdateHoliday
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateHolidaysServiceTest {

    private lateinit var updateHolidaysService: UpdateHolidaysService
    private lateinit var updateHoliday: UpdateHoliday
    private lateinit var mapper: UpdateHolidaysRequestMapper

    @BeforeEach
    fun setup() {
        // Mock dependencies
        updateHoliday = mockk()
        mapper = mockk()

        // Initialize service with mocked dependencies  
        updateHolidaysService = UpdateHolidaysService(mapper, updateHoliday)
    }

    @Test
    fun `should update holiday successfully with valid request`() = runBlocking {

        val validRequest = UpdateHolidaysRequestTestFactory.build()

        // Mock the mapper and domain call
        coEvery { mapper.toDomain(validRequest) } returns mockk()
        coEvery { updateHoliday.updateHoliday(any()) } returns 1

        assertDoesNotThrow {
            runBlocking {
                updateHolidaysService.invoke(validRequest)
            }
        }

        coVerify(exactly = 1) { mapper.toDomain(validRequest) }
        coVerify(exactly = 1) { updateHoliday.updateHoliday(any()) }
    }

    @Test
    fun `should throw exception when start date is in the past`() = runBlocking {

        val invalidRequest = UpdateHolidaysRequestTestFactory.buildInvalidStartDate()

        val exception = assertThrows(CfmsException::class.java) {
            runBlocking {
                updateHolidaysService.invoke(invalidRequest)
            }
        }

        assertEquals("Start date cannot be before today's date.", exception.message)
        coVerify(exactly = 0) { updateHoliday.updateHoliday(any()) }
    }

    @Test
    fun `should throw exception when end date is before start date`() = runBlocking {

        val invalidRequest = UpdateHolidaysRequestTestFactory.buildInvalidEndDate()

        val exception = assertThrows(CfmsException::class.java) {
            runBlocking {
                updateHolidaysService.invoke(invalidRequest)
            }
        }

        assertEquals("End date cannot be before start date.", exception.message)
        coVerify(exactly = 0) { updateHoliday.updateHoliday(any()) }
    }

    @Test
    fun `should update holiday with null holiday name`() = runBlocking {

        val requestWithNullHolidayName = UpdateHolidaysRequestTestFactory.buildWithNullHolidayName()

        // Mock the mapper and domain call
        coEvery { mapper.toDomain(requestWithNullHolidayName) } returns mockk()
        coEvery { updateHoliday.updateHoliday(any()) } returns 1

        assertDoesNotThrow {
            runBlocking {
                updateHolidaysService.invoke(requestWithNullHolidayName)
            }
        }

        coVerify(exactly = 1) { mapper.toDomain(requestWithNullHolidayName) }
        coVerify(exactly = 1) { updateHoliday.updateHoliday(any()) }
    }

    @Test
    fun `should update holiday with null backup franchise ids`() = runBlocking {

        val requestWithNullBackupFranchiseIds = UpdateHolidaysRequestTestFactory.buildWithNullBackupFranchiseIds()

        // Mock the mapper and domain call
        coEvery { mapper.toDomain(requestWithNullBackupFranchiseIds) } returns mockk()
        coEvery { updateHoliday.updateHoliday(any()) } returns 1

        assertDoesNotThrow {
            runBlocking {
                updateHolidaysService.invoke(requestWithNullBackupFranchiseIds)
            }
        }

        coVerify(exactly = 1) { mapper.toDomain(requestWithNullBackupFranchiseIds) }
        coVerify(exactly = 1) { updateHoliday.updateHoliday(any()) }
    }

    @Test
    fun `should log and throw CfmsException when domain update fails`() = runBlocking {

        val validRequest = UpdateHolidaysRequestTestFactory.build()

        // Mock the mapper and domain call to throw an exception
        coEvery { mapper.toDomain(validRequest) } returns mockk()
        coEvery { updateHoliday.updateHoliday(any()) } throws CfmsException("Domain error")

        val exception = assertThrows(CfmsException::class.java) {
            runBlocking {
                updateHolidaysService.invoke(validRequest)
            }
        }

        assertEquals("Exception occurred while updating holiday: Domain error", exception.message)
        coVerify(exactly = 1) { mapper.toDomain(validRequest) }
        coVerify(exactly = 1) { updateHoliday.updateHoliday(any()) }
    }
}
*/
