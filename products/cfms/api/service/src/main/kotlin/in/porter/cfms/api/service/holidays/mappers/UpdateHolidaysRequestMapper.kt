package `in`.porter.cfms.api.service.holidays.mappers

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

class UpdateHolidaysRequestMapper
@Inject
constructor() {

    fun toDomain(holidayId: Long, request: UpdateHolidaysRequest): UpdateHolidayEntity {
        val today = LocalDate.now()

        // 1. Validate that end date is not before today
        if (request.endDate?.isBefore(today)  == true ) {
            throw CfmsException("End date cannot be before today.")
        }

        // 2. Validate that start date is not after end date
        if (request.startDate?.isAfter(request.endDate) == true) {
            throw CfmsException("Start date cannot be after end date.")
        }

        // Create a domain object with the necessary fields
        return UpdateHolidayEntity(
            id = holidayId,
            franchiseId = request.franchiseId,
            startDate = request.startDate,
            endDate = request.endDate,
            holidayName = request.holidayName,
            leaveType = LeaveType.valueOf((request.leaveType?.name).toString()),
            backupFranchiseIds = request.backupFranchiseIds,
            updatedAt = Instant.now()  // Set updatedAt to current date
        )
    }
}
