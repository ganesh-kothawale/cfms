package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
import `in`.porter.cfms.api.service.utils.CommonUtils
import `in`.porter.cfms.domain.holidays.usecases.CreateHoliday
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class CreateHolidaysService
@Inject
constructor(
    private val createHoliday: CreateHoliday,  // Renamed to CreateHoliday
    private val mapper: CreateHolidaysRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(CreateHolidaysService::class.java)

    suspend fun invoke(request: CreateHolidaysRequest): String {

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
        val generatedHolidayId = CommonUtils.generateRandomAlphaNumeric(10)

        // Map the CreateHolidaysRequest to a domain Holiday object
        val holiday = mapper.toDomain(request, generatedHolidayId)

        try {

            val holidayId = createHoliday.invoke(holiday)
            logger.info("Holiday stored successfully with ID: {}", holidayId)

            // Create audit log after the holiday is successfully created
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = generatedHolidayId,
                    entityType = "Holiday",
                    status = "Created",
                    message = "Holiday created successfully",
                    //TODO : currently passing hardcoded user ID once UserID development is done need to replace with actual user ID value
                    updatedBy = 123
                )
            )

            return generatedHolidayId

        } catch (e: CfmsException) {
            // Validation or business rule failure
            logger.error("Business validation error: {}", e.message)
            throw e  // Re-throw the CfmsException with the same message
        } catch (e: Exception) {
            // Handle unexpected exceptions
            logger.error("Error while storing holiday in DB: {}", e.message, e)
            throw CfmsException("Failed to store holiday in DB: ${e.message}")
        }
    }
}
