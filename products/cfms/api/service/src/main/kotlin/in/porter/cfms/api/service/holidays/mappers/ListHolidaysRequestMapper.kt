package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.holidays.LeaveType
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysDomainRequest
import java.time.LocalDate
import javax.inject.Inject

class ListHolidaysRequestMapper
@Inject
constructor() {
    fun toDomain(req: ListHolidaysRequest): ListHolidaysDomainRequest {
        return ListHolidaysDomainRequest(
            page = req.page,
            size = req.size,
            franchiseIds = req.franchiseIds,
            backupFranchises = req.backupFranchises,
            leaveType = req.leaveType,
            createdDate = req.createdDate,
            updatedDate = req.updatedDate,
            fromDate = req.fromDate,
            toDate = req.toDate
        )
    }
}

