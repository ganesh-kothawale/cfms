package `in`.porter.cfms.domain.holidays.factories

import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import java.time.Instant
import java.time.LocalDate

object UpdateHolidayFactory {

    fun build(
        holidayId: Int = 1,
        franchiseId: String = "ABC12",
        startDate: LocalDate = LocalDate.now().plusDays(1),
        endDate: LocalDate = LocalDate.now().plusDays(2),
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
        holidayName: String? = "Christmas",
        updatedAt: Instant? = Instant.now(),
        createdAt: Instant? = Instant.now()
    ): UpdateHoliday {
        return UpdateHoliday(
            holidayId = holidayId,
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            holidayName = holidayName,
            updatedAt = updatedAt,
            createdAt = createdAt
        )
    }

    fun buildInvalidDate(
        holidayId: Int = 1,
        franchiseId: String = "ABC12",
        startDate: LocalDate = LocalDate.now().minusDays(1),  // Invalid date (start date before today)
        endDate: LocalDate = LocalDate.now().plusDays(2),
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
        holidayName: String? = "Christmas",
        updatedAt: Instant? = Instant.now(),
        createdAt: Instant? = Instant.now()
    ): UpdateHoliday {
        return UpdateHoliday(
            holidayId = holidayId,
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            holidayName = holidayName,
            updatedAt = updatedAt,
            createdAt = createdAt
        )
    }

    fun buildWithNullFields(
        holidayId: Int = 1,
        franchiseId: String = "ABC12",
        startDate: LocalDate = LocalDate.now().plusDays(1),
        endDate: LocalDate = LocalDate.now().plusDays(2),
        leaveType: LeaveType = LeaveType.Normal,
        holidayName: String? = null,  // Null holiday name
        backupFranchiseIds: String? = null,  // Null backup franchises
        updatedAt: Instant? = Instant.now(),
        createdAt: Instant? = Instant.now()
    ): UpdateHoliday {
        return UpdateHoliday(
            holidayId = holidayId,
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            holidayName = holidayName,
            updatedAt = updatedAt,
            createdAt = createdAt
        )
    }
}
