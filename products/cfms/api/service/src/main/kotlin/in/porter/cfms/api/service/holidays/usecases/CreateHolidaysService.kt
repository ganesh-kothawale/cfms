package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
import `in`.porter.cfms.domain.holidays.usecases.RecordHoliday
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class CreateHolidaysService
@Inject
constructor(
    private val mapper: CreateHolidaysRequestMapper,
    private val recordHoliday: RecordHoliday
) {

    private val logger = LoggerFactory.getLogger(CreateHolidaysService::class.java)

    suspend fun invoke(request: CreateHolidaysRequest): Long {

        val today = LocalDate.now()

        // 1. Validate that start date is not before today
        if (request.start_date.isBefore(today)) {
            logger.warn("Invalid start date: {}", request.start_date)
            throw CfmsException("Start date cannot be before today's date.")
        }

        // 2. Validate that end date is after start date
        if (request.end_date.isBefore(request.start_date)) {
            logger.warn("End date {} is before start date {}", request.end_date, request.start_date)
            throw CfmsException("End date cannot be before start date.")
        }

        // 4. Map the CreateHolidaysRequest to a domain Holiday object using the mapper
        val holidayDomain = mapper.toDomain(request)

        // 5. Store the holiday in the database
        return try {
            logger.info("Storing holiday in DB for franchise ID {}", request.franchise_id)
            recordHoliday.invoke(holidayDomain).also {
                logger.info("Holiday created successfully with ID: {}", it)
            }
        } catch (e: Exception) {
            logger.error("Error storing holiday in DB: {}", e.message, e)
            throw CfmsException("Failed to store holiday in DB: ${e.message}")
        }
    }
}
