package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import javax.inject.Inject

class CreateHolidaysRequestMapper
@Inject
constructor() {
    fun toDomain(request: CreateHolidaysRequest): Holiday {
        return Holiday(
            franchiseId = request.franchiseId,
            startDate = request.startDate,
            endDate = request.endDate,
            holidayName = request.holidayName,
            leaveType = mapLeaveTypeToDomain(request.leaveType),  // Assuming leaveType is already mapped correctly
            backupFranchiseIds = request.backupFranchiseIds,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }

    // Map the API leave type to the domain leave type
    private fun mapLeaveTypeToDomain(apiLeaveType: `in`.porter.cfms.api.models.holidays.LeaveType): LeaveType {
        return when (apiLeaveType) {
            `in`.porter.cfms.api.models.holidays.LeaveType.Normal -> LeaveType.Normal
            `in`.porter.cfms.api.models.holidays.LeaveType.Emergency -> LeaveType.Emergency
        }
    }
}
