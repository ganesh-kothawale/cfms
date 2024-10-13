package `in`.porter.cfms.data.holidays.records.factories

import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import java.time.LocalDate

object UpdateHolidayRecordFactory {

    fun create(
        holidayId: Int = 1,
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
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
