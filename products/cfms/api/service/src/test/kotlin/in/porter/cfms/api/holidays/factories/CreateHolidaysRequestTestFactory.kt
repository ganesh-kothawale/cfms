package `in`.porter.cfms.api.holidays.factories

import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.models.holidays.LeaveType

import java.time.LocalDate

object CreateHolidaysRequestTestFactory {

    fun buildCreateHolidaysRequest(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now().plusDays(1),
        endDate: LocalDate = LocalDate.now().plusDays(2),
        holidayName: String = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String = "321,456"
    ): CreateHolidaysRequest {
        return CreateHolidaysRequest(
            franchise_id = franchiseId,
            start_date = startDate,
            end_date = endDate,
            holiday_name = holidayName,
            leave_type = leaveType,
            backup_franchise_ids = backupFranchiseIds
        )
    }
}
