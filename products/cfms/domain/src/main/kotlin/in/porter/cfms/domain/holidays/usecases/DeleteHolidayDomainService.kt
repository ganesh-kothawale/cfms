package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveRequest
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class DeleteHolidayDomainService
@Inject constructor(
    private val holidayRepo: HolidayRepo,
    private val courierService: CourierApplyLeaveCallingService
) {

    private val logger = LoggerFactory.getLogger(DeleteHolidayDomainService::class.java)

    suspend fun deleteHoliday(holidayId: Int) {
        try {
            // Step 1: Fetch the holiday by ID
            val holiday = holidayRepo.getById(holidayId)
            if(holiday == null){
                throw CfmsException("Holiday not found with ID $holidayId")
            }

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
                applyLeaveResponse = courierService.applyLeave(applyLeaveRequest)
            } catch (e: CfmsException) {
                logger.error("Error applying leave: ${e.message}")
                throw CfmsException("Failed to apply leave: ${e.message}")
            }

            // Log the success message from the external API
            logger.info("Leave Cancelled successfully at courier: ${applyLeaveResponse.message}")

            // Step 5: Delete the holiday from the database
            holidayRepo.deleteById(holidayId)

        } catch (e: CfmsException) {
            logger.error("Error deleting holiday: ${e.message}")
            throw CfmsException(e.message)
        } catch (e: Exception) {
            logger.error("Unexpected error deleting holiday: ${e.message}")
            throw Exception("Unexpected error occurred while deleting the holiday.")
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
