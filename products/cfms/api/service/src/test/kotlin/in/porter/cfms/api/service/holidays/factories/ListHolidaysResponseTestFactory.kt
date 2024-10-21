package `in`.porter.cfms.api.service.holidays.factories

import `in`.porter.cfms.api.models.holidays.FranchiseAddress
import `in`.porter.cfms.api.models.holidays.FranchisePoc
import `in`.porter.cfms.api.models.holidays.FranchiseResponse
import `in`.porter.cfms.api.models.holidays.HolidayDetails
import `in`.porter.cfms.api.models.holidays.HolidayPeriod
import `in`.porter.cfms.api.models.holidays.HolidayResponse
import `in`.porter.cfms.api.models.holidays.ListHolidaysResponse
import java.time.LocalDate

object ListHolidaysResponseTestFactory {
    fun defaultResponse(): ListHolidaysResponse {
        return ListHolidaysResponse(
            page = 1,
            size = 10,
            totalPages = 1,
            totalRecords = 1,
            holidays = listOf(
                HolidayResponse(
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
                    franchise = FranchiseResponse(
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
            )
        )
    }
}