package `in`.porter.cfms.api.holidays.usecases

import `in`.porter.cfms.api.holidays.factories.CreateHolidaysRequestMapperFactory
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.entities.Holiday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class CreateHolidaysRequestMapperTest {

    private val mapper = CreateHolidaysRequestMapper()

    @Test
    fun `should map CreateHolidaysRequest to Holiday domain entity`() {
        val request: CreateHolidaysRequest = CreateHolidaysRequestMapperFactory.buildCreateHolidaysRequest()

        val result: Holiday = mapper.toDomain(request)

        assertEquals(request.franchise_id, result.franchiseId)
        assertEquals(request.start_date, result.startDate)
        assertEquals(request.end_date, result.endDate)
        assertEquals(request.holiday_name, result.holidayName)
        assertEquals(request.backup_franchise_ids, result.backupFranchiseIds)
    }
}
