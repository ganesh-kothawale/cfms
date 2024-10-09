package `in`.porter.cfms.data.repos.factories

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import java.time.Instant
import java.time.LocalDate

object PsqlHolidayRepoFactory {

    fun buildHoliday(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): Holiday {
        return Holiday(
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

    fun buildUpdateHolidayEntity(
        holidayId: Int = 1,
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): UpdateHoliday {
        return UpdateHoliday(
            holidayId = holidayId,
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
