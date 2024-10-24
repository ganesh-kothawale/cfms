/*
package `in`.porter.cfms.api.service.holidays.factories

import `in`.porter.cfms.api.models.holidays.LeaveType
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import java.time.LocalDate

object UpdateHolidaysRequestTestFactory {

    fun build(
        holidayId: Int = 123,
        franchiseId: String = "ABC12",
        startDate: LocalDate = LocalDate.now().plusDays(1),
        endDate: LocalDate = LocalDate.now().plusDays(2),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "SME001"
    ): UpdateHolidaysRequest {
        return UpdateHolidaysRequest(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            holidayId = holidayId
        )
    }

    // Add methods for generating negative cases or boundary cases if needed
    fun buildInvalidStartDate(): UpdateHolidaysRequest {
        return build(
            startDate = LocalDate.now().minusDays(1) // Invalid: Start date in the past
        )
    }

    fun buildInvalidEndDate(): UpdateHolidaysRequest {
        return build(
            startDate = LocalDate.now().plusDays(5),
            endDate = LocalDate.now().plusDays(1) // Invalid: End date before start date
        )
    }

    fun buildWithNullHolidayName(): UpdateHolidaysRequest {
        return build(
            holidayName = null
        )
    }

    fun buildWithNullBackupFranchiseIds(): UpdateHolidaysRequest {
        return build(
            backupFranchiseIds = null
        )
    }
}*/
