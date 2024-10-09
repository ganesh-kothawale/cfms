package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.usecases.CreateHoliday
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class CreateHolidaysService
@Inject
constructor(
    private val createHoliday: CreateHoliday,  // Renamed to CreateHoliday
    private val mapper: CreateHolidaysRequestMapper
) {

    private val logger = LoggerFactory.getLogger(CreateHolidaysService::class.java)

    suspend fun invoke(request: CreateHolidaysRequest): Int {

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

        // Map the CreateHolidaysRequest to a domain Holiday object
        val holiday = mapper.toDomain(request)

        try {
            // Delegate the responsibility of checking existing holidays and storing the holiday
            val holidayId = createHoliday.createHoliday(holiday)
            logger.info("Holiday stored successfully with ID: {}", holidayId)
            return holidayId

        } catch (e: Exception) {
            logger.error("Error while storing holiday in DB: {}", e.message, e)
            throw CfmsException("Failed to store holiday in DB: ${e.message}")
        }
    }
}
