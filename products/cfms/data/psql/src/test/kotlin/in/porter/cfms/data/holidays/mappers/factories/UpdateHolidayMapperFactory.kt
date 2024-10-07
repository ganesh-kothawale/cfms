package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import java.time.Instant
import java.time.LocalDate

object UpdateHolidayMapperFactory {

    fun buildUpdateHolidayEntity(
        holidayId: Int = 1,
        franchiseId: String = "F001",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant? = Instant.now(),
        updatedAt: Instant? = Instant.now()
    ): UpdateHolidayEntity {
        return UpdateHolidayEntity(
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

    fun buildUpdateHolidayRecord(
        holidayId: Int = 1,
        franchiseId: String = "F001",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant? = Instant.now(),
        updatedAt: Instant? = Instant.now()
    ): UpdateHolidayRecord {
        return UpdateHolidayRecord(
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
