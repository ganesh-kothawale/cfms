package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.LocalDate

object HolidayRowMapperFactory {

    fun buildMockResultRow(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): ResultRow {
        // Mock ResultRow object
        val row = mockk<ResultRow>(relaxed = true)

        // Mock fields for the ResultRow object
        every { row[HolidayTable.franchiseId] } returns franchiseId
        every { row[HolidayTable.startDate] } returns startDate
        every { row[HolidayTable.endDate] } returns endDate
        every { row[HolidayTable.holidayName] } returns holidayName
        every { row[HolidayTable.leaveType] } returns leaveType.name
        every { row[HolidayTable.backupFranchiseIds] } returns backupFranchiseIds
        every { row[HolidayTable.createdAt] } returns createdAt
        every { row[HolidayTable.updatedAt] } returns updatedAt

        return row
    }

    fun buildHolidayRecord(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): HolidayRecord {
        return HolidayRecord(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
