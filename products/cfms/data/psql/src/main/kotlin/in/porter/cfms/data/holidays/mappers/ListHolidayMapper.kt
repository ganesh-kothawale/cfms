package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.data.holidays.records.ListHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.FranchiseAddress
import `in`.porter.cfms.domain.holidays.entities.FranchisePoc
import `in`.porter.cfms.domain.holidays.entities.HolidayDetails
import `in`.porter.cfms.domain.holidays.entities.HolidayPeriod
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ListHolidayMapper
@Inject
constructor() {

    // Map ListHolidayRecord to domain ListHoliday
    fun toDomain(record: ListHolidayRecord, franchise: ListHolidaysFranchise): ListHoliday {
        return ListHoliday(
            holidayId = record.holidayId,
            franchiseId = record.franchiseId,
            holidayPeriod = HolidayPeriod(
                fromDate = record.startDate,
                toDate = record.endDate
            ),
            holidayDetails = HolidayDetails(
                name = record.holidayName,
                leaveType = record.leaveType,
                backupFranchise = record.backupFranchiseIds
            ),
            franchise = franchise
        )
    }

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
            franchiseId = resultRow[FranchisesTable.franchiseId], // Ensure this field is available
            franchiseName = resultRow[FranchisesTable.porterHubName],
            poc = FranchisePoc(
                name = resultRow[FranchisesTable.pocName],
                contact = resultRow[FranchisesTable.primaryNumber]
            ),
            address = FranchiseAddress(
                gpsAddress = resultRow[FranchisesTable.address],
                city = resultRow[FranchisesTable.city],
                state = resultRow[FranchisesTable.state]
            )
        )
    )

}
