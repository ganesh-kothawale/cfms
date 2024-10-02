package `in`.porter.cfms.api.holidays.factories

import `in`.porter.cfms.api.models.holidays.LeaveType
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import java.time.LocalDate


object UpdateHolidaysRequestMapperFactory {

    fun buildUpdateHolidaysRequest(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456"
    ): UpdateHolidaysRequest {
        return UpdateHolidaysRequest(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds
        )
    }
}