package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import javax.inject.Inject

class UpdateHolidayMapper
@Inject
constructor()
{

    // Map UpdateHolidayEntity to HolidayRecord (for updating in the database)
    fun toRecord(updateHoliday: UpdateHolidayEntity): UpdateHolidayRecord {
        return UpdateHolidayRecord(
            holidayId = updateHoliday.holidayId,  // Ensure ID is mapped for updating
            franchiseId = updateHoliday.franchiseId.toString(),
            startDate = updateHoliday.startDate,
            endDate = updateHoliday.endDate,
            holidayName = updateHoliday.holidayName,
            leaveType = updateHoliday.leaveType,
            backupFranchiseIds = updateHoliday.backupFranchiseIds,
            updatedAt = updateHoliday.updatedAt,
            createdAt = updateHoliday.createdAt
        )
    }

    // Optionally, map from database record to UpdateHolidayEntity (if needed for fetching)
    fun toDomain(record: UpdateHolidayRecord): UpdateHolidayEntity {
        return UpdateHolidayEntity(
            holidayId = record.holidayId,
            franchiseId = record.franchiseId,
            startDate = record.startDate,
            endDate = record.endDate,
            holidayName = record.holidayName,
            leaveType = record.leaveType,
            backupFranchiseIds = record.backupFranchiseIds,
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }
}
