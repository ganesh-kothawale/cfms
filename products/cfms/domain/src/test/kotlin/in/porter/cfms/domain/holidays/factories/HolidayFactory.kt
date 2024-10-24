/*
package `in`.porter.cfms.domain.holidays.factories

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import java.time.LocalDate

object HolidayFactory {
    fun buildHoliday(
        franchiseId: String = "ABC12",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "SME001",
        holidayName: String? = "Christmas",
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): Holiday {
        return Holiday(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            holidayName = holidayName,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
*/
