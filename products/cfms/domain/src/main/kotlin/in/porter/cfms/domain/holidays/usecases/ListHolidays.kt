package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysDomainRequest
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class ListHolidays
@Inject
constructor(
    private val holidayRepo: HolidayRepo
) {

    private val logger = LoggerFactory.getLogger(ListHolidays::class.java)

    suspend fun invoke(request : ListHolidaysDomainRequest): HolidaySearchResult {
        logger.info("Received request to list holidays:{}", request)

        validateAndProcessLeaveType(request.leaveType)

        if (request.fromDate != null && request.toDate != null && request.fromDate.isAfter(request.toDate)) {
            logger.error("Start date cannot be after end date: startDate={}, endDate={}", request.fromDate, request.toDate)
            throw CfmsException("Start date cannot be after end date.")
        }

        return try {
            // Fetch holidays and count from the repository
            logger.info("Fetching holidays from repository for request: {}", request)
            val holidays = holidayRepo.findHolidays(request)
            val totalRecords = holidayRepo.countHolidays(request)

            logger.info("Fetched {} holidays with totalRecords: {}", holidays.size, totalRecords)

            // Return result
            HolidaySearchResult(
                totalRecords = totalRecords,
                data = holidays
            )
        } catch (e: Exception) {
            logger.error("Error fetching holidays from repository: {}", e.message, e)
            throw CfmsException("Failed to retrieve holidays from the database.")
        }
    }

    private fun validateAndProcessLeaveType(leaveType: String?): LeaveType? {
        logger.info("Validating and processing leaveType: {}", leaveType?.trim())

        return if (leaveType.isNullOrBlank()) {
            logger.info("No leaveType provided, returning null")
            null
        } else {
            try {
                LeaveType.valueOf(leaveType.trim())  // Convert input to LeaveType enum
            } catch (e: IllegalArgumentException) {
                logger.error("Invalid leaveType value: {}", leaveType)
                throw CfmsException("Invalid leaveType value: $leaveType")  // Clear message for invalid leaveType
            }
        }
    }
}
