/*
package `in`.porter.cfms.data.holidays.mappers.usecases


import `in`.porter.cfms.data.holidays.mappers.ListHolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.factories.ListHolidayRowMapperFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListHolidayRowMapperTest {

    private lateinit var listHolidayRowMapper: ListHolidayRowMapper

    @BeforeEach
    fun setup() {
        listHolidayRowMapper = ListHolidayRowMapper()
    }

    @Test
    fun `should map ResultRow to ListHoliday`() {
        val resultRow = ListHolidayRowMapperFactory.createResultRow()

        val result = listHolidayRowMapper.toRecord(resultRow)

        assertEquals(1, result.holidayId)
        assertEquals("franchise-123", result.franchiseId)
        assertEquals("New Year Holiday", result.holidayDetails.name)
        assertEquals("Normal", result.holidayDetails.leaveType)
        assertEquals("backup-123", result.holidayDetails.backupFranchise)
        assertEquals("123 Main St", result.franchise.address.gpsAddress)
        assertEquals("Sample City", result.franchise.address.city)
        assertEquals("Sample State", result.franchise.address.state)
    }
}
*/
