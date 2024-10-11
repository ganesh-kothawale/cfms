package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.data.holidays.FranchisesTable
import `in`.porter.cfms.data.holidays.HolidayTable
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDate

object ListHolidayRowMapperFactory {
    fun createResultRow(): ResultRow {
        val resultRow = mockk<ResultRow>()
        every { resultRow[HolidayTable.holidayId] } returns 1
        every { resultRow[HolidayTable.franchiseId] } returns "franchise-123"
        every { resultRow[HolidayTable.startDate] } returns LocalDate.of(2024, 1, 1)
        every { resultRow[HolidayTable.endDate] } returns LocalDate.of(2024, 1, 10)
        every { resultRow[HolidayTable.holidayName] } returns "New Year Holiday"
        every { resultRow[HolidayTable.leaveType] } returns "Normal"
        every { resultRow[HolidayTable.backupFranchiseIds] } returns "backup-123"
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
