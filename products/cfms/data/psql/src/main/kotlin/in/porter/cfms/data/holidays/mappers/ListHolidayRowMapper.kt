package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.domain.holidays.entities.FranchiseAddress
import `in`.porter.cfms.domain.holidays.entities.FranchisePoc
import `in`.porter.cfms.domain.holidays.entities.HolidayDetails
import `in`.porter.cfms.domain.holidays.entities.HolidayPeriod
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ListHolidayRowMapper
@Inject
constructor() {

    fun toRecord(resultRow: ResultRow) = ListHoliday(
        holidayId = resultRow[HolidayTable.holidayId],
        franchiseId = resultRow[HolidayTable.franchiseId],
        holidayPeriod = HolidayPeriod(
            fromDate = resultRow[HolidayTable.startDate],
            toDate = resultRow[HolidayTable.endDate]
        ),
        holidayDetails = HolidayDetails(
            name = resultRow[HolidayTable.holidayName],
            leaveType = resultRow[HolidayTable.leaveType],
            backupFranchise = resultRow[HolidayTable.backupFranchiseIds]
        ),
        franchise = ListHolidaysFranchise(
            franchiseId = resultRow[HolidayTable.franchiseId],
            franchiseName = resultRow[FranchisesTable.porterHubName],
            poc = FranchisePoc(
                name = resultRow[FranchisesTable.pocName],
                contact = resultRow[FranchisesTable.primaryNumber]
            ),
            address = FranchiseAddress(
                gpsAddress = resultRow[FranchisesTable.address],
                city = resultRow[FranchisesTable.city],  // Add these fields if needed, or fetch later
                state = resultRow[FranchisesTable.state],  // Placeholder values
            )
        )
    )
}
