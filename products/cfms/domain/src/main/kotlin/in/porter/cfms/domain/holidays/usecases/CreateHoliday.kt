package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveRequest
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class CreateHoliday
@Inject
constructor(
    private val holidayRepo: HolidayRepo,
    private val courierApplyLeaveCallingService: CourierApplyLeaveCallingService
) {

    private val logger = LoggerFactory.getLogger(CreateHoliday::class.java)

    suspend fun invoke(holiday: Holiday): String {
        // Check if a holiday already exists for the given franchiseId and dates
        val existingHoliday = holidayRepo.getByIdAndDate(holiday.franchiseId, holiday.startDate, holiday.endDate)
        if (existingHoliday != null) {
            throw CfmsException("Holiday is already applied for the given dates.")
        }

        // Prepare the ApplyLeaveRequest for the external API call
        val applyLeaveRequest = ApplyLeaveRequest(
            identification_code = holiday.franchiseId,
            holidays = generateHolidayDates(holiday.startDate, holiday.endDate), // Generate dates between start and end
            type = holiday.leaveType.name,
            backup_franchises = holiday.backupFranchiseIds,
            status = "Approved"
        )

        // Call the external API to apply the leave
        val applyLeaveResponse: ApplyLeaveResponse
        try {
            applyLeaveResponse = courierApplyLeaveCallingService.applyLeave(applyLeaveRequest)
        } catch (e: CfmsException) {
            logger.error("Error applying leave through external API for franchiseId: ${holiday.franchiseId}. Error: ${e.message}")
            throw CfmsException("Failed to apply leave through external API: ${e.message}")
        } catch (e: Exception) {
            logger.error("Unexpected error during external API call for franchiseId: ${holiday.franchiseId}. Error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred during leave application.")
        }

        // Log the success message from the external API
        logger.info("Leave applied successfully: ${applyLeaveResponse.message}")

        // Insert the holiday into the database
        try {
            return holidayRepo.record(holiday)
        } catch (e: Exception) {
            logger.error("Error inserting holiday into the database for franchiseId: ${holiday.franchiseId}. Error: ${e.message}", e)
            throw CfmsException("Failed to store holiday in the database: ${e.message}")
        }
    }

    // Helper method to generate a list of dates between start and end date
    private fun generateHolidayDates(startDate: LocalDate, endDate: LocalDate): List<String> {
        val dates = mutableListOf<String>()
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate.toString())
            currentDate = currentDate.plusDays(1)
        }
        return dates
    }
}
