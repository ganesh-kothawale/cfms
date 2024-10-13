package `in`.porter.cfms.data.holidays.records.usecases

import `in`.porter.cfms.data.holidays.records.factories.HolidayRecordFactory
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HolidayRecordTest {

    private lateinit var franchiseId: String
    private lateinit var startDate: LocalDate
    private lateinit var endDate: LocalDate
    private lateinit var holidayName: String
    private lateinit var leaveType: LeaveType
    private lateinit var backupFranchiseIds: String
    private lateinit var createdAt: Instant
    private lateinit var updatedAt: Instant

    @BeforeEach
    fun setup() {
        // Initialize default values for reuse
        franchiseId = "123"
        startDate = LocalDate.now()
        endDate = LocalDate.now().plusDays(1)
        holidayName = "Test Holiday"
        leaveType = LeaveType.Normal
        backupFranchiseIds = "321,456"
        createdAt = Instant.now()
        updatedAt = Instant.now()
    }

    @Test
    fun `should create a valid HolidayRecord`() {
        // Act
        val holidayRecord = HolidayRecordFactory.build(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

        // Assert
        assertEquals(franchiseId, holidayRecord.franchiseId)
        assertEquals(startDate, holidayRecord.startDate)
        assertEquals(endDate, holidayRecord.endDate)
        assertEquals(holidayName, holidayRecord.holidayName)
        assertEquals(leaveType, holidayRecord.leaveType)
        assertEquals(backupFranchiseIds, holidayRecord.backupFranchiseIds)
        assertEquals(createdAt, holidayRecord.createdAt)
        assertEquals(updatedAt, holidayRecord.updatedAt)
    }
}
