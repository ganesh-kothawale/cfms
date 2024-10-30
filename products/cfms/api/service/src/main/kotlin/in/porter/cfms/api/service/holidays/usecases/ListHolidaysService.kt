package `in`.porter.cfms.api.service.holidays.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.FranchiseAddress
import `in`.porter.cfms.api.models.holidays.FranchisePoc
import `in`.porter.cfms.api.models.holidays.FranchiseResponse
import `in`.porter.cfms.api.models.holidays.HolidayDetails
import `in`.porter.cfms.api.models.holidays.HolidayPeriod
import `in`.porter.cfms.api.models.holidays.HolidayResponse
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import `in`.porter.cfms.api.models.holidays.ListHolidaysResponse
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import `in`.porter.cfms.domain.holidays.usecases.ListHolidays
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListHolidaysService
@Inject
constructor(
    private val listHolidays: ListHolidays
) {

    private val logger = LoggerFactory.getLogger(ListHolidaysService::class.java)
    suspend fun invoke(request: ListHolidaysRequest): ListHolidaysResponse {
        try {
            // Fetch data from domain layer (HolidaySearchResult)
            val holidaysResult = listHolidays.invoke(
                franchiseId = request.franchiseId,
                leaveType = request.leaveType,
                startDate = request.startDate,
                endDate = request.endDate,
                page = request.page,
                size = request.size
            )

            logger.info("Fetched holidays: {}", holidaysResult)

            // Ensure holidaysResult has totalRecords and data
            return ListHolidaysResponse(
                page = request.page,
                size = request.size,
                totalPages = calculateTotalPages(holidaysResult.totalRecords, request.size),
                totalRecords = holidaysResult.totalRecords,  // This property must be defined in holidaysResult
                holidays = holidaysResult.data.map { holiday ->
                    mapToHolidayResponse(holiday)  // Map each holiday to HolidayResponse
                }
            )
        } catch (e: CfmsException) {
            logger.error("Holiday listing failed: ${e.message}", e)
            throw e
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred while retrieving holidays.")
        }
    }

    private fun calculateTotalPages(totalRecords: Int, pageSize: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, pageSize: {}", totalRecords, pageSize)
        return if (totalRecords == 0) 0 else (totalRecords + pageSize - 1) / pageSize
    }

    private fun mapToHolidayResponse(holiday: ListHoliday): HolidayResponse {
        logger.info("Mapping holiday: {}", holiday)
        return HolidayResponse(
            holidayId = holiday.holidayId,
            franchiseId = holiday.franchiseId,
            holidayPeriod = HolidayPeriod(
                fromDate = holiday.holidayPeriod.fromDate,
                toDate = holiday.holidayPeriod.toDate
            ),
            holidayDetails = HolidayDetails(
                name = holiday.holidayDetails.name,
                leaveType = holiday.holidayDetails.leaveType.toString(),  // Ensure this is converted to a string
                backupFranchise = holiday.holidayDetails.backupFranchise
            ),
            franchise = mapToFranchiseResponse(holiday.franchise)
        )
    }

    private fun mapToFranchiseResponse(franchise: ListHolidaysFranchise): FranchiseResponse {
        logger.info("Mapping franchise: {}", franchise)
        return FranchiseResponse(
            franchiseId = franchise.franchiseId,
            franchiseName = franchise.franchiseName,
            poc = FranchisePoc(
                name = franchise.poc.name,
                contact = franchise.poc.contact
            ),
            address = FranchiseAddress(
                gpsAddress = franchise.address.gpsAddress,
                city = franchise.address.city,  // Replace with actual data if available
                state = franchise.address.state // Replace with actual data if available
            )
        )
    }
}
