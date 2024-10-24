/*
package `in`.porter.cfms.data.holidays.records.usecases

import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.data.holidays.records.factories.UpdateHolidayRecordFactory
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class UpdateHolidayRecordTest {

    @Test
    fun `should create UpdateHolidayRecord with default values`() {
        val expectedInstant = Instant.now().truncatedTo(ChronoUnit.MILLIS)

        val record = HolidayRecord(
            holidayId = "1",
            franchiseId = "123",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            holidayName = "Test Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "456",
            createdAt = expectedInstant,
            updatedAt = expectedInstant
        )

        assertEquals(expectedInstant, record.createdAt?.truncatedTo(ChronoUnit.MILLIS))
        assertEquals(expectedInstant, record.updatedAt?.truncatedTo(ChronoUnit.MILLIS))
    }

    @Test
    fun `should create UpdateHolidayRecord with custom values`() {
        val record = UpdateHolidayRecordFactory.create(
            holidayId = "2",
            franchiseId = "456",
            startDate = LocalDate.of(2024, 12, 24),
            endDate = LocalDate.of(2024, 12, 26),
            holidayName = "New Year",
            leaveType = LeaveType.Emergency,
            backupFranchiseIds = "654,987"
        )

        assertEquals(2, record.holidayId)
        assertEquals("456", record.franchiseId)
        assertEquals(LocalDate.of(2024, 12, 24), record.startDate)
        assertEquals(LocalDate.of(2024, 12, 26), record.endDate)
        assertEquals("New Year", record.holidayName)
        assertEquals(LeaveType.Emergency, record.leaveType)
        assertEquals("654,987", record.backupFranchiseIds)
    }
}
*/
