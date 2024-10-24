package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveRequest
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class DeleteHoliday
@Inject constructor(
    private val holidayRepo: HolidayRepo,
    private val courierService: CourierApplyLeaveCallingService
) {

    private val logger = LoggerFactory.getLogger(DeleteHoliday::class.java)

    suspend fun invoke(holidayId: String) {
        try {
            logger.info("Attempting to delete holiday with ID: $holidayId")
            // Step 1: Fetch the holiday by ID
            val holiday = holidayRepo.getById(holidayId)
                ?: throw CfmsException("Holiday not found with ID $holidayId")

            logger.info("Holiday found: $holiday")

            // Step 2: Validate that current date is after the start date
            if (LocalDate.now().isAfter(holiday.startDate)) {
                throw CfmsException("Holiday cannot be deleted after the allowed time window")
            }

            // Step 3: Call external API with status "Cancelled"
            val applyLeaveRequest = ApplyLeaveRequest(
                //TODO: Need to change the identification code 
                identification_code = holiday.franchiseId.toString(),
                holidays = generateHolidayDates(
                    holiday.startDate,
                    holiday.endDate
                ), // Generate dates between start and end
                type = holiday.leaveType.name,
                backup_franchises = holiday.backupFranchiseIds?.split(",") ?: emptyList(),
                status = "Cancelled"
            )

            val applyLeaveResponse: ApplyLeaveResponse
            try {
                logger.info("Sending cancellation request to courier service")
                applyLeaveResponse = courierService.applyLeave(applyLeaveRequest)
            } catch (e: CfmsException) {
                logger.error("Error applying leave cancellation: ${e.message}")
                throw CfmsException("Failed to apply leave cancellation: ${e.message}")
            }

            // Log the success message from the external API
            logger.info("Leave Cancelled successfully at courier: ${applyLeaveResponse.message}")

            // Step 5: Delete the holiday from the database
            holidayRepo.deleteById(holidayId)
            logger.info("Holiday with ID $holidayId deleted successfully")

        } catch (e: CfmsException) {
            logger.error("Exception occurred: ${e.message}")
            throw e // Re-throw original CfmsException without changing it

        } catch (e: Exception) {
            logger.error("Unexpected error occurred: ${e.message}", e)
            throw CfmsException("Unexpected error occurred while deleting the holiday.")
        }
    }

    // Helper method to generate a list of dates between start and end date
    private fun generateHolidayDates(startDate: LocalDate, endDate: LocalDate): List<String> {
        logger.info("Generating holiday dates between $startDate and $endDate")
        val dates = mutableListOf<String>()
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate.toString())
            currentDate = currentDate.plusDays(1)
        }
        logger.info("Generated ${dates.size} dates")
        return dates
    }
}
