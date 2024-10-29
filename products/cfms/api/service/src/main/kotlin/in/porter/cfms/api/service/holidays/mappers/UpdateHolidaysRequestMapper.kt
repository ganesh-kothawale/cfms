package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import org.slf4j.LoggerFactory
import java.time.Instant
import javax.inject.Inject

class UpdateHolidaysRequestMapper
@Inject
constructor() {
    private val logger = LoggerFactory.getLogger(UpdateHolidaysRequestMapper::class.java)

    fun toDomain(request: UpdateHolidaysRequest): Holiday {

        logger.info("Mapping UpdateHolidayRequest to UpdateHolidayEntity")

        // Create a domain object with the necessary fields
        return Holiday(
            holidayId = request.holidayId,
            franchiseId = request.franchiseId,
            startDate = request.startDate,
            endDate = request.endDate,
            holidayName = request.holidayName,
            leaveType = LeaveType.valueOf((request.leaveType.name)),
            backupFranchiseIds = request.backupFranchiseIds,
            updatedAt = Instant.now(),
            createdAt = Instant.now(),
        )
    }
}
