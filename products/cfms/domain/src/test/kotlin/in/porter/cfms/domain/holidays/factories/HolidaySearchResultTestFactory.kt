/*
package `in`.porter.cfms.domain.holidays.factories

import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.FranchiseAddress
import `in`.porter.cfms.domain.holidays.entities.FranchisePoc
import `in`.porter.cfms.domain.holidays.entities.HolidayDetails
import `in`.porter.cfms.domain.holidays.entities.HolidayPeriod
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import `in`.porter.cfms.domain.holidays.usecases.HolidaySearchResult
import java.time.LocalDate

object HolidaySearchResultTestFactory {

    fun createHolidaySearchResult(records: Int = 1): HolidaySearchResult {
        return HolidaySearchResult(
            totalRecords = records,
            data = List(records) { createListHoliday() }
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
                leaveType = LeaveType.Normal.toString(),
                backupFranchise = "backup-123"
            ),
            franchise = ListHolidaysFranchise(
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
        )
    }
}
*/
