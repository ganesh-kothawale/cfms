package `in`.porter.cfms.api.holidays.usecases

import `in`.porter.cfms.api.holidays.factories.UpdateHolidaysRequestMapperFactory
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.UpdateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UpdateHolidaysRequestMapperTest {

    private val mapper = UpdateHolidaysRequestMapper()

    @Test
    fun `should map UpdateHolidaysRequest to UpdateHolidayEntity`() {
        val request: UpdateHolidaysRequest = UpdateHolidaysRequestMapperFactory.buildUpdateHolidaysRequest()

        val result: UpdateHoliday = mapper.toDomain(request)
        assertEquals(request.holidayId, result.holidayId)
        assertEquals(request.franchiseId, result.franchiseId)
        assertEquals(request.startDate, result.startDate)
        assertEquals(request.endDate, result.endDate)
        assertEquals(request.holidayName, result.holidayName)
        assertEquals(request.backupFranchiseIds, result.backupFranchiseIds)
    }

    @Test
    fun `should throw CfmsException when endDate is before today`() {
        val request: UpdateHolidaysRequest = UpdateHolidaysRequestMapperFactory.buildUpdateHolidaysRequest(
            endDate = LocalDate.now().minusDays(1)
        )

        val exception = assertThrows(CfmsException::class.java) {
            mapper.toDomain(request)
        }

        assertEquals("End date cannot be before today.", exception.message)
    }

    @Test
    fun `should throw CfmsException when startDate is after endDate`() {
        val request: UpdateHolidaysRequest = UpdateHolidaysRequestMapperFactory.buildUpdateHolidaysRequest(
            startDate = LocalDate.now().plusDays(2),
            endDate = LocalDate.now().plusDays(1)
        )

        val exception = assertThrows(CfmsException::class.java) {
            mapper.toDomain(request)
        }

        assertEquals("Start date cannot be after end date.", exception.message)
    }
}
