/*
package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.Instant
import java.time.LocalDate
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HolidayMapperTest {

    private lateinit var holidayMapper: HolidayMapper

    @BeforeAll
    fun setup() {
        holidayMapper = HolidayMapper()
    }

    @Test
    fun `should map Holiday to HolidayRecord`() {
        val holiday = Holiday(
            franchiseId = "123",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            holidayName = "Test Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "321,456",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val holidayRecord = holidayMapper.toRecord(holiday)

        assertEquals(holiday.franchiseId, holidayRecord.franchiseId)
        assertEquals(holiday.startDate, holidayRecord.startDate)
        assertEquals(holiday.endDate, holidayRecord.endDate)
        assertEquals(holiday.holidayName, holidayRecord.holidayName)
        assertEquals(holiday.leaveType, holidayRecord.leaveType)
        assertEquals(holiday.backupFranchiseIds, holidayRecord.backupFranchiseIds)

        // Assert that createdAt and updatedAt are not null
        assertNotNull(holidayRecord.createdAt)
        assertNotNull(holidayRecord.updatedAt)

        // Using custom isCloseTo for Instant comparison, with safe calls
        assertTrue(
            holidayRecord.createdAt.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true,
            "createdAt should be close to Instant.now()"
        )
        assertTrue(
            holidayRecord.updatedAt.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true,
            "updatedAt should be close to Instant.now()"
        )
    }

    @Test
    fun `should map HolidayRecord to Holiday`() {
        val holidayRecord = HolidayRecord(
            franchiseId = "123",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            holidayName = "Test Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "321,456",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val holiday = holidayMapper.toDomain(holidayRecord)

        assertEquals(holidayRecord.franchiseId, holiday.franchiseId)
        assertEquals(holidayRecord.startDate, holiday.startDate)
        assertEquals(holidayRecord.endDate, holiday.endDate)
        assertEquals(holidayRecord.holidayName, holiday.holidayName)
        assertEquals(holidayRecord.leaveType, holiday.leaveType)
        assertEquals(holidayRecord.backupFranchiseIds, holiday.backupFranchiseIds)

        // Assert that createdAt and updatedAt are not null
        assertNotNull(holiday.createdAt)
        assertNotNull(holiday.updatedAt)

        // Using custom isCloseTo for Instant comparison, with safe calls
        assertTrue(
            holiday.createdAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true,
            "createdAt should be close to Instant.now()"
        )
        assertTrue(
            holiday.updatedAt?.isCloseTo(Instant.now(), Duration.ofSeconds(1)) == true,
            "updatedAt should be close to Instant.now()"
        )
    }

    // Custom isCloseTo function
    fun Instant.isCloseTo(other: Instant, tolerance: Duration): Boolean {
        val difference = Duration.between(this, other).abs()
        return difference <= tolerance
    }
}
*/
