package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

class UpdateHolidaysRequestMapper
@Inject
constructor() {
    private val logger = LoggerFactory.getLogger(UpdateHolidaysRequestMapper::class.java)

    fun toDomain(request: UpdateHolidaysRequest): UpdateHolidayEntity {

        logger.info("Mapping UpdateHolidayRequest to UpdateHolidayEntity")

        // Get today's date
        val today = LocalDate.now()

        // 1. Validate that end date is not before today
        if (request.endDate.isBefore(today)) {
            throw CfmsException("End date cannot be before today.")
        }

        // 2. Validate that start date is not after end date
        if (request.startDate.isAfter(request.endDate) ) {
            throw CfmsException("Start date cannot be after end date.")
        }

        // Create a domain object with the necessary fields
        return UpdateHolidayEntity(
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
