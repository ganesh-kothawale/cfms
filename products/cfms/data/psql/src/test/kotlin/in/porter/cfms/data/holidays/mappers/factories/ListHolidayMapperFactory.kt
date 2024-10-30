/*
package `in`.porter.cfms.data.holidays.mappers.factories

import `in`.porter.cfms.domain.holidays.entities.FranchiseAddress
import `in`.porter.cfms.domain.holidays.entities.FranchisePoc
import `in`.porter.cfms.domain.holidays.entities.HolidayDetails
import `in`.porter.cfms.domain.holidays.entities.HolidayPeriod
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import `in`.porter.cfms.data.holidays.records.ListHolidayRecord
import java.time.LocalDate

object ListHolidayMapperFactory {
    fun createListHolidayRecord(): ListHolidayRecord {
        return ListHolidayRecord(
            holidayId = 1,
            franchiseId = "franchise-123",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 1, 10),
            holidayName = "New Year Holiday",
            leaveType = "Normal",
            backupFranchiseIds = "backup-123"
        )
    }

    fun createFranchise(): ListHolidaysFranchise {
        return ListHolidaysFranchise(
            franchiseId = "franchise-123",
            franchiseName = "Franchise A",
            poc = FranchisePoc(
                name = "John Doe",
                contact = "1234567890"
            ),
            address = FranchiseAddress(
                gpsAddress = "123 Main St",
                city = "Sample City",
                state = "Sample State"
            )
        )
    }

    fun createListHoliday(): ListHoliday {
        return ListHoliday(
            holidayId = 1,
            franchiseId = "franchise-123",
            holidayPeriod = HolidayPeriod(
                fromDate = LocalDate.of(2024, 1, 1),
                toDate = LocalDate.of(2024, 1, 10)
            ),
            holidayDetails = HolidayDetails(
                name = "New Year Holiday",
                leaveType = "Normal",
                backupFranchise = "backup-123"
            ),
            franchise = createFranchise()
        )
    }
}
*/
