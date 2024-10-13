package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.UpdateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.usecases.UpdateHoliday
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class UpdateHolidaysService
@Inject
constructor(
    private val mapper: UpdateHolidaysRequestMapper,  // Mapper to convert API request to domain object
    private val updateHoliday: UpdateHoliday          // Domain use case to handle holiday updates
) {

    private val logger = LoggerFactory.getLogger(UpdateHolidaysService::class.java)

    suspend fun invoke(request: UpdateHolidaysRequest): Int {
        // Validate franchise ID cannot be changed
        logger.info("Invoke function called to send the data to domain for holiday ID: {}", request.holidayId)
        val today = LocalDate.now()

        // 1. Validate that start date is not before today
        if (request.startDate.isBefore(today)) {
            logger.warn("Invalid start date: {}", request.startDate)
            throw CfmsException("Start date cannot be before today's date.")
        }

        // 2. Validate that end date is after start date
        if (request.endDate.isBefore(request.startDate)) {
            logger.warn("End date {} is before start date {}", request.endDate, request.startDate)
            throw CfmsException("End date cannot be before start date.")
        }

        try {
            // Map API request to domain object using the mapper
            val holidayDomain = mapper.toDomain(request)

            // Invoke domain logic to update holiday
            return updateHoliday.updateHoliday(holidayDomain)
        }catch (e: CfmsException){

            logger.error("Exception occurred while updating holiday: {}", e.message)
            throw CfmsException("Exception occurred while updating holiday: ${e.message}")
        }
        catch (e: Exception){
            logger.error("Error occurred while updating holiday: {}", e.message)
            throw CfmsException("Error occurred while updating holiday: ${e.message}")
        }
    }
}
