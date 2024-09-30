package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import java.time.Instant
import javax.inject.Inject

class CreateHolidaysRequestMapper
@Inject
constructor() {

    fun toDomain(req: CreateHolidaysRequest): Holiday {
        return Holiday(
            franchiseId = req.franchise_id,
            startDate = req.start_date,
            endDate = req.end_date,
            holidayName = req.holiday_name,
            leaveType = LeaveType.valueOf(req.leave_type.name),
            backupFranchiseIds = req.backup_franchise_ids,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
