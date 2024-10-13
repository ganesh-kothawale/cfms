package `in`.porter.cfms.data.holidays.records.usecases

import `in`.porter.cfms.data.holidays.records.factories.ListHolidayRecordFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ListHolidayRecordFactoryTest {

    @Test
    fun `should create default ListHolidayRecord`() {
        val defaultRecord = ListHolidayRecordFactory.createDefault()

        assertEquals(1, defaultRecord.holidayId)
        assertEquals("franchise-123", defaultRecord.franchiseId)
        assertEquals(LocalDate.of(2024, 1, 1), defaultRecord.startDate)
        assertEquals(LocalDate.of(2024, 1, 10), defaultRecord.endDate)
        assertEquals("New Year Holiday", defaultRecord.holidayName)
        assertEquals("Normal", defaultRecord.leaveType)
        assertEquals("backup-123", defaultRecord.backupFranchiseIds)
    }

    @Test
    fun `should create custom ListHolidayRecord`() {
        val customRecord = ListHolidayRecordFactory.createCustom(
            holidayId = 2,
            franchiseId = "franchise-456",
            startDate = LocalDate.of(2025, 5, 1),
            endDate = LocalDate.of(2025, 5, 5),
            holidayName = "Labor Day Holiday",
            leaveType = "Emergency",
            backupFranchiseIds = "backup-456"
        )

        assertEquals(2, customRecord.holidayId)
        assertEquals("franchise-456", customRecord.franchiseId)
        assertEquals(LocalDate.of(2025, 5, 1), customRecord.startDate)
        assertEquals(LocalDate.of(2025, 5, 5), customRecord.endDate)
        assertEquals("Labor Day Holiday", customRecord.holidayName)
        assertEquals("Emergency", customRecord.leaveType)
        assertEquals("backup-456", customRecord.backupFranchiseIds)
    }
}
