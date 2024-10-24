/*
package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import java.time.LocalDate

object HolidayMapperFactory {

    fun buildHoliday(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ) = Holiday(
        franchiseId = franchiseId,
        startDate = startDate,
        endDate = endDate,
        holidayName = holidayName,
        leaveType = leaveType,
        backupFranchiseIds = backupFranchiseIds,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun buildHolidayRecord(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ) = HolidayRecord(
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
*/
