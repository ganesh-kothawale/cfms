/*
package `in`.porter.cfms.data.holidays.mappers.usecases

import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.factories.HolidayRowMapperFactory
import `in`.porter.cfms.data.holidays.mappers.factories.UpdateHolidayRowMapperFactory
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UpdateHolidayRowMapperTest {

    private val updateHolidayRowMapper = HolidayRowMapper()

    @Test
    fun `should map ResultRow to UpdateHolidayRecord`() {
        val resultRow = UpdateHolidayRowMapperFactory.create()

        val updateHolidayRecord = updateHolidayRowMapper.toRecord(resultRow)

        assertEquals(1, updateHolidayRecord.holidayId)
        assertEquals("123", updateHolidayRecord.franchiseId)
        assertEquals(LocalDate.now(), updateHolidayRecord.startDate)
        assertEquals(LocalDate.now().plusDays(1), updateHolidayRecord.endDate)
        assertEquals("Christmas", updateHolidayRecord.holidayName)
        assertEquals(LeaveType.Normal, updateHolidayRecord.leaveType)
        assertEquals("321,456", updateHolidayRecord.backupFranchiseIds)
    }

    @Test
    fun `should build a valid UpdateHolidayRecord`() {
        val updateHolidayRecord = HolidayRowMapperFactory.buildHolidayRecord()

        assertEquals(1, updateHolidayRecord.holidayId)
        assertEquals("123", updateHolidayRecord.franchiseId)
        assertEquals(LocalDate.now(), updateHolidayRecord.startDate)
        assertEquals(LocalDate.now().plusDays(1), updateHolidayRecord.endDate)
        assertEquals("Christmas", updateHolidayRecord.holidayName)
        assertEquals(LeaveType.Normal, updateHolidayRecord.leaveType)
        assertEquals("321,456", updateHolidayRecord.backupFranchiseIds)
    }
}
*/
