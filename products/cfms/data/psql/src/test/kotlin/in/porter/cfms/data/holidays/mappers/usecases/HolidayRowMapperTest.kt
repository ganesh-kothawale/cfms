package `in`.porter.cfms.data.holidays.mappers.usecases

import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.factories.HolidayRowMapperFactory
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import io.mockk.clearAllMocks
import org.jetbrains.exposed.sql.ResultRow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HolidayRowMapperTest {

    private lateinit var holidayRowMapper: HolidayRowMapper

    @BeforeEach
    fun setUp() {
        holidayRowMapper = HolidayRowMapper()
        clearAllMocks()
    }

    @Test
    fun `toRecord should map ResultRow to HolidayRecord`() {
        // Arrange
        val resultRow: ResultRow = HolidayRowMapperFactory.buildMockResultRow()

        // Act
        val holidayRecord: HolidayRecord = holidayRowMapper.toRecord(resultRow)

        // Assert
        assertEquals(resultRow[HolidayTable.franchiseId], holidayRecord.franchiseId)
        assertEquals(resultRow[HolidayTable.startDate], holidayRecord.startDate)
        assertEquals(resultRow[HolidayTable.endDate], holidayRecord.endDate)
        assertEquals(resultRow[HolidayTable.holidayName], holidayRecord.holidayName)
        assertEquals(resultRow[HolidayTable.leaveType], holidayRecord.leaveType.name)
        assertEquals(resultRow[HolidayTable.backupFranchiseIds], holidayRecord.backupFranchiseIds)
        assertEquals(resultRow[HolidayTable.createdAt], holidayRecord.createdAt)
        assertEquals(resultRow[HolidayTable.updatedAt], holidayRecord.updatedAt)
    }
}
