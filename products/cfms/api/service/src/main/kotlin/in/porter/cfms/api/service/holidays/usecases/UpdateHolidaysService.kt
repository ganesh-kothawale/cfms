package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.UpdateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.usecases.UpdateHoliday
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateHolidaysService
@Inject
constructor(
    private val mapper: UpdateHolidaysRequestMapper,  // Mapper to convert API request to domain object
    private val updateHoliday: UpdateHoliday          // Domain use case to handle holiday updates
) {

    private val logger = LoggerFactory.getLogger(UpdateHolidaysService::class.java)

    suspend fun invoke(holidayId: Long, request: UpdateHolidaysRequest): Long {
        // Validate franchise ID cannot be changed
        if (request.franchise_id?.isBlank() == true) {
            throw CfmsException("Franchise ID is required and cannot be empty.")
        }

        // Map API request to domain object using the mapper
        val holidayDomain = mapper.toDomain(holidayId, request)

        // Invoke domain logic to update holiday
        return updateHoliday.updateHoliday(holidayDomain)
    }
}
