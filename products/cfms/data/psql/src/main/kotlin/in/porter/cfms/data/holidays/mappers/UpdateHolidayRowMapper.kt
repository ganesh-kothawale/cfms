package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class UpdateHolidayRowMapper
@Inject
constructor() {

    fun toRecord(resultRow: ResultRow) = UpdateHolidayRecord(
        holidayId = resultRow[HolidayTable.holidayId],
        franchiseId = resultRow[HolidayTable.franchiseId],
        startDate = resultRow[HolidayTable.startDate],
        endDate = resultRow[HolidayTable.endDate],
        holidayName = resultRow[HolidayTable.holidayName],
        leaveType = LeaveType.valueOf(resultRow[HolidayTable.leaveType]),
        backupFranchiseIds = resultRow[HolidayTable.backupFranchiseIds],
        createdAt = resultRow[HolidayTable.createdAt],
        updatedAt = resultRow[HolidayTable.updatedAt]
    )
}