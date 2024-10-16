package `in`.porter.cfms.data.franchise.mappers

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.franchise.records.ListFranchisesRecord
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject
class ListFranchisesRowMapper
@Inject
constructor(
    private val holidayRepo: HolidayRepo
) {

    private val logger = LoggerFactory.getLogger(ListFranchisesRowMapper::class.java)
    suspend fun toRecord(resultRow: ResultRow): ListFranchisesRecord {

        val franchiseId = resultRow[FranchisesTable.franchiseId]

        // Get the filtered holidays for the franchise
        val holidays = getFilteredHolidays(franchiseId)


        logger.info("Mapping result row to ListFranchisesRecord")
        return ListFranchisesRecord(
            franchiseId = resultRow[FranchisesTable.franchiseId],
            latitude = resultRow[FranchisesTable.latitude],
            longitude = resultRow[FranchisesTable.longitude],
            address = resultRow[FranchisesTable.address],
            city = resultRow[FranchisesTable.city],
            state = resultRow[FranchisesTable.state],
            pincode = resultRow[FranchisesTable.pincode],
            pocName = resultRow[FranchisesTable.pocName],
            pocMobile = resultRow[FranchisesTable.primaryNumber],
            pocEmail = resultRow[FranchisesTable.email],
            radiusCovered = resultRow[FranchisesTable.radiusCoverage],
            hlpEnabled = resultRow[FranchisesTable.hlpEnabled],
            isActive = resultRow[FranchisesTable.status],
            daysOfTheWeek = resultRow[FranchisesTable.daysOfOperation],
            cutOffTime = resultRow[FranchisesTable.cutOffTime],  // Directly extract as String
            startTime = resultRow[FranchisesTable.startTime],    // Directly extract as String
            endTime = resultRow[FranchisesTable.endTime],        // Handle nullable values      // Convert to string using toString()
            holidays = holidays,
            porterHubName = resultRow[FranchisesTable.porterHubName],
            franchiseGst = resultRow[FranchisesTable.franchiseGst],
            franchisePan = resultRow[FranchisesTable.franchisePan],
            franchiseCanceledCheque = resultRow[FranchisesTable.franchiseCanceledCheque],
            courierPartners = listOf("DHL", "FedEx", "UPS"),
            kamUser = resultRow[FranchisesTable.kamUser],
            createdAt = resultRow[FranchisesTable.createdAt].toString(),
            updatedAt = resultRow[FranchisesTable.updatedAt].toString()
        )

    }

    // Function to fetch holidays and filter them as per the requirements
    private suspend fun getFilteredHolidays(franchiseId: String): List<String> {
        val today = LocalDate.now()
        val result = mutableSetOf<LocalDate>()

        // Retrieve holidays from HolidayRepo
        val holidaysList = holidayRepo.get(franchiseId)
        if (holidaysList.isEmpty()) {
            return emptyList()
        }

        // Loop over each holiday and apply the required logic
        for (holiday in holidaysList) {
            // Check if the holiday is active (i.e., startDate <= today <= endDate)
            if (!holiday.endDate.isBefore(today)) {  // This checks if the holiday is not before today
                // Step 3: Add each date from startDate to endDate into the result list
                var currentDate = holiday.startDate

                // Ensure we include only active/future dates
                if (currentDate.isBefore(today)) {
                    currentDate = today  // Skip to today if startDate is in the past
                }

                // Iterate from currentDate to endDate
                while (!currentDate.isAfter(holiday.endDate)) {
                    result.add(currentDate)  // Add the current date to the result list
                    currentDate = currentDate.plusDays(1)  // Move to the next day
                }
            }
        }

        // Return the final list of holiday dates
        return result.sorted().map { it.toString() }
    }
}