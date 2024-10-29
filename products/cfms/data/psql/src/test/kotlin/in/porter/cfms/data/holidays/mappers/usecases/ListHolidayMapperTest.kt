/*
package `in`.porter.cfms.data.holidays.mappers.usecases

import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.factories.ListHolidayMapperFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListHolidayMapperTest {

    private lateinit var listHolidayMapper: ListHolidayMapper

    @BeforeEach
    fun setup() {
        listHolidayMapper = ListHolidayMapper()
    }

    @Test
    fun `should map ListHolidayRecord to ListHoliday`() {
        val record = ListHolidayMapperFactory.createListHolidayRecord()
        val franchise = ListHolidayMapperFactory.createFranchise()

        val result = listHolidayMapper.toDomain(record, franchise)

        assertEquals(record.holidayId, result.holidayId)
        assertEquals(record.franchiseId, result.franchiseId)
        assertEquals(record.startDate, result.holidayPeriod.fromDate)
        assertEquals(record.endDate, result.holidayPeriod.toDate)
        assertEquals(record.holidayName, result.holidayDetails.name)
        assertEquals(record.leaveType, result.holidayDetails.leaveType)
        assertEquals(record.backupFranchiseIds, result.holidayDetails.backupFranchise)
        assertEquals(franchise, result.franchise)
    }
}
*/
