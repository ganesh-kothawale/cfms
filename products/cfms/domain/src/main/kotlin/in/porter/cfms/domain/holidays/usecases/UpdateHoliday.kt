package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveRequest
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveResponse
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class UpdateHoliday
@Inject
constructor(
    private val holidayRepo: HolidayRepo,
    private val courierService: CourierApplyLeaveCallingService  // External service call
) {
    private val logger = LoggerFactory.getLogger(UpdateHoliday::class.java)

    suspend fun updateHoliday(holiday: UpdateHolidayEntity): Int {
        // Fetch the existing holiday by ID

        logger.info("Checking validations before updating holiday: ${holiday.holidayId}")
        val existingHoliday = holidayRepo.getById(holiday.holidayId)
            ?: throw CfmsException("Holiday with ID ${holiday.holidayId} not found.")

        // Validate franchise ID cannot be updated
        if (existingHoliday.franchiseId != holiday.franchiseId) {
            throw CfmsException("Franchise ID cannot be updated.")
        }

        // Handle date-based update logic
        val today = LocalDate.now()

        if (existingHoliday.endDate.isBefore(today)) {
            throw CfmsException("Cannot update a holiday whose end date has already passed.")
        }

        if (existingHoliday.startDate.isBefore(today)) {
            // Only update end date and other fields, but not start date
            holiday.copy(
                startDate = existingHoliday.startDate // Prevent updating start date
            )
        }

        logger.info("Calling courier service apply leave API")

        // Prepare the ApplyLeaveRequest for the external API call
        val applyLeaveRequest = ApplyLeaveRequest(
            identification_code = holiday.franchiseId.toString(),
            holidays = generateHolidayDates(holiday.startDate, holiday.endDate), // Generate dates between start and end
            type = holiday.leaveType.name,
            backup_franchises = holiday.backupFranchiseIds?.split(",") ?: emptyList(),
            status = "Approved"
        )

        // Call the external API to apply the leave
        val applyLeaveResponse: ApplyLeaveResponse
        try {
            applyLeaveResponse = courierService.applyLeave(applyLeaveRequest)
        } catch (e: CfmsException) {
            logger.error("Error applying leave: ${e.message}")
            throw CfmsException("Failed to apply leave: ${e.message}")
        }

        // Log the success message from the external API
        logger.info("Leave applied successfully at courier: ${applyLeaveResponse.message}")

        // Insert the holiday into the database

        val existingHolidayCheck = holidayRepo.getByIdAndDate(holiday.franchiseId.toString(), holiday.startDate, holiday.endDate)
        if (existingHolidayCheck != null) {
            throw CfmsException("Holiday is already applied for the given dates.")
        }
        return holidayRepo.update(holiday)
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
