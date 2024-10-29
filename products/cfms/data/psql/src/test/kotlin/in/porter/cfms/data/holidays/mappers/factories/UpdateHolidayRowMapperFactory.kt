/*
package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import io.mockk.coEvery
import io.mockk.mockk
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.LocalDate

object UpdateHolidayRowMapperFactory {

    fun create(): ResultRow {
        val resultRow: ResultRow = mockk(relaxed = true)
        // You can mock individual field values as well using relaxed mocks
        coEvery { resultRow[HolidayTable.holidayId] } returns 1
        coEvery { resultRow[HolidayTable.franchiseId] } returns "123"
        coEvery { resultRow[HolidayTable.startDate] } returns LocalDate.now()
        coEvery { resultRow[HolidayTable.endDate] } returns LocalDate.now().plusDays(1)
        coEvery { resultRow[HolidayTable.holidayName] } returns "Christmas"
        coEvery { resultRow[HolidayTable.leaveType] } returns LeaveType.Normal.name
        coEvery { resultRow[HolidayTable.backupFranchiseIds] } returns "321,456"
        coEvery { resultRow[HolidayTable.createdAt] } returns Instant.now()
        coEvery { resultRow[HolidayTable.updatedAt] } returns Instant.now()

        return resultRow
    }

    fun buildUpdateHolidayRecord(): UpdateHolidayRecord {
        return UpdateHolidayRecord(
            holidayId = 1,
            franchiseId = "123",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            holidayName = "Christmas",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "321,456",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
*/
