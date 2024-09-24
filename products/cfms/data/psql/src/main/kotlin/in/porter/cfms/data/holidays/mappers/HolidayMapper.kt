package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import javax.inject.Inject

class HolidayMapper
@Inject
constructor() {

    fun toDomain(record: HolidayRecord) = Holiday(
        franchiseId = record.franchiseId,
        startDate = record.startDate,
        endDate = record.endDate,
        holidayName = record.holidayName,
        leaveType = record.leaveType,
        backupFranchiseIds = record.backupFranchiseIds,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )

    fun toRecord(holiday: Holiday) = HolidayRecord(
        franchiseId = holiday.franchiseId,
        startDate = holiday.startDate,
        endDate = holiday.endDate,
        holidayName = holiday.holidayName,
        leaveType = holiday.leaveType,
        backupFranchiseIds = holiday.backupFranchiseIds,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )
}