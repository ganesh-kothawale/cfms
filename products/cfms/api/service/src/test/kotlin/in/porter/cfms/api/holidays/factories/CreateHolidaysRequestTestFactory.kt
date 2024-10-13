package `in`.porter.cfms.api.holidays.factories

import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.models.holidays.LeaveType

import java.time.LocalDate

object CreateHolidaysRequestTestFactory {

    fun buildCreateHolidaysRequest(
        franchiseId: String = "ABC12",
        startDate: LocalDate = LocalDate.now().plusDays(1),
        endDate: LocalDate = LocalDate.now().plusDays(2),
        holidayName: String = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String = "SME001"
    ): CreateHolidaysRequest {
        return CreateHolidaysRequest(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds
        )
    }
}
