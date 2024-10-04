package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreateHoliday
@Inject
constructor(
    private val holidayRepo: HolidayRepo
) {

    private val logger = LoggerFactory.getLogger(CreateHoliday::class.java)

    suspend fun createHoliday(holiday: Holiday): Long {

        // 1. Check if a holiday already exists for the given franchise ID, start date, and end date
        val existingHoliday = holidayRepo.getByIdAndDate(holiday.franchiseId, holiday.startDate, holiday.endDate)

        if (existingHoliday != null) {
            logger.warn("Holiday already exists for franchise {} from {} to {}", holiday.franchiseId, holiday.startDate, holiday.endDate)
            throw CfmsException("Holiday is already applied by franchise ID ${holiday.franchiseId} from ${holiday.startDate} to ${holiday.endDate}.")
        }

        // 2. Store the holiday in the repository and return the generated holiday ID
        return holidayRepo.record(holiday)
    }
}
