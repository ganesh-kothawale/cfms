package `in`.porter.cfms.api.service.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CreateHolidaysService
@Inject
constructor(
    private val holidayRepo: HolidayRepo
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

        // 4. Check if a holiday already exists for the given franchise ID, start date, and end date

        val existingHoliday = holidayRepo.getByIdAndDate(request.franchise_id, request.start_date, request.end_date)

        if (existingHoliday != null) {
            // Throw an exception if the holiday already exists
            throw CfmsException("Holiday is already applied by franchise ID ${request.franchise_id} from ${request.start_date} to ${request.end_date}.")
        }

        // Map the CreateHolidaysRequest to a domain Holiday object
        val holiday = Holiday(
            franchiseId = request.franchise_id,
            startDate = request.start_date,
            endDate = request.end_date,
            holidayName = request.holiday_name,
            leaveType = LeaveType.valueOf(request.leave_type.name),
            backupFranchiseIds = request.backup_franchise_ids,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )

        try {
            logger.info("Attempting to store holiday in DB")
            // Record the holiday and get the generated holiday ID
            val holidayId = holidayRepo.record(holiday)
            logger.info("Holiday stored successfully with ID: {}", holidayId)

            return holidayId

        } catch (e: Exception) {
            logger.error("Error while storing holiday in DB: {}", e.message, e)
            throw CfmsException("Failed to store holiday in DB: ${e.message}")
        }
    }
}