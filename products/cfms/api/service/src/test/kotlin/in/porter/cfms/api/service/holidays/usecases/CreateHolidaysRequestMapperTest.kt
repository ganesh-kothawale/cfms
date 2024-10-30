/*
package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.service.holidays.factories.CreateHolidaysRequestMapperTestFactory
import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateHolidaysRequestMapperTest {

    private lateinit var mapper: CreateHolidaysRequestMapper

    @BeforeEach
    fun setUp() {
        mapper = CreateHolidaysRequestMapper()
    }

    @Test
    fun `should map CreateHolidaysRequest to Holiday domain object`() {
        // Arrange
        val request = CreateHolidaysRequestMapperTestFactory.createRequest()

        // Act
        val domainHoliday = mapper.toDomain(request)

        // Assert
        assertEquals(request.franchiseId, domainHoliday.franchiseId)
        assertEquals(request.startDate, domainHoliday.startDate)
        assertEquals(request.endDate, domainHoliday.endDate)
        assertEquals(request.holidayName, domainHoliday.holidayName)
        assertEquals(LeaveType.Normal, domainHoliday.leaveType)
        assertEquals(request.backupFranchiseIds, domainHoliday.backupFranchiseIds)
    }
}
*/
