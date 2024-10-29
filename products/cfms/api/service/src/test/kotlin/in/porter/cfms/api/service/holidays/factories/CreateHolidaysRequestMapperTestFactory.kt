/*
package `in`.porter.cfms.api.service.holidays.factories

import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import java.time.LocalDate

object CreateHolidaysRequestMapperTestFactory {

    // Create a test CreateHolidaysRequest object
    fun createRequest(
        franchiseId: String = "ABC123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: `in`.porter.cfms.api.models.holidays.LeaveType = `in`.porter.cfms.api.models.holidays.LeaveType.Normal,
        backupFranchiseIds: String? = null
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

    // Create a test Holiday domain object
    fun createHoliday(
        franchiseId: String = "ABC123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Test Holiday",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = null,
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): Holiday {
        return Holiday(
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
*/
