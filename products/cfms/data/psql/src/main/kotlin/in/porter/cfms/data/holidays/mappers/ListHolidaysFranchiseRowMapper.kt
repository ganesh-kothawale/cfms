package `in`.porter.cfms.data.holidays.mappers

import `in`.porter.cfms.data.holidays.FranchisesTable
import `in`.porter.cfms.domain.holidays.entities.FranchiseAddress
import `in`.porter.cfms.domain.holidays.entities.FranchisePoc
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ListHolidaysFranchiseRowMapper
@Inject
constructor() {
    fun toRecord(resultRow: ResultRow): ListHolidaysFranchise {
        return ListHolidaysFranchise(
            franchiseId = resultRow[FranchisesTable.franchiseId],
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
    }
}