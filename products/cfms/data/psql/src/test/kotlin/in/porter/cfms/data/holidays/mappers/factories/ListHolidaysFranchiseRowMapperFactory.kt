package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.data.franchise.FranchisesTable
import org.jetbrains.exposed.sql.ResultRow
import io.mockk.every
import io.mockk.mockk

object ListHolidaysFranchiseRowMapperFactory {
    fun createResultRow(): ResultRow {
        val resultRow = mockk<ResultRow>()
        every { resultRow[FranchisesTable.franchiseId] } returns "franchise-123"
        every { resultRow[FranchisesTable.porterHubName] } returns "Franchise A"
        every { resultRow[FranchisesTable.pocName] } returns "John Doe"
        every { resultRow[FranchisesTable.primaryNumber] } returns "1234567890"
        every { resultRow[FranchisesTable.address] } returns "123 Main St"
        every { resultRow[FranchisesTable.city] } returns "Sample City"
        every { resultRow[FranchisesTable.state] } returns "Sample State"

        return resultRow
    }
}
