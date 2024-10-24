/*
package `in`.porter.cfms.data.holidays.repos.factories

import `in`.porter.cfms.domain.holidays.entities.FranchiseAddress
import `in`.porter.cfms.domain.holidays.entities.FranchisePoc
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.HolidayDetails
import `in`.porter.cfms.domain.holidays.entities.HolidayPeriod
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import java.time.Instant
import java.time.LocalDate

object PsqlHolidayRepoFactory {

    fun buildHoliday(
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): Holiday {
        return Holiday(
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun buildUpdateHolidayEntity(
        holidayId: Int = 1,
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: LeaveType = LeaveType.Normal,
        backupFranchiseIds: String? = "321,456",
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): UpdateHoliday {
        return UpdateHoliday(
            holidayId = holidayId,
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun buildListHoliday(
        holidayId: Int = 1,
        franchiseId: String = "123",
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plusDays(1),
        holidayName: String? = "Christmas",
        leaveType: String = "Normal",
        backupFranchiseIds: String? = "321,456",
        franchise: ListHolidaysFranchise = buildListHolidaysFranchise()
    ): ListHoliday {
        return ListHoliday(
            holidayId = holidayId,
            franchiseId = franchiseId,
            holidayPeriod = HolidayPeriod(
                fromDate = startDate,
                toDate = endDate
            ),
            holidayDetails = HolidayDetails(
                name = holidayName,
                leaveType = leaveType,
                backupFranchise = backupFranchiseIds
            ),
            franchise = franchise
        )
    }

    fun buildListHolidaysFranchise(
        franchiseId: String = "123",
        franchiseName: String = "Porter Franchise",
        pocName: String = "John Doe",
        contact: String = "1234567890",
        gpsAddress: String = "123 Main St",
        city: String = "Sample City",
        state: String = "Sample State"
    ): ListHolidaysFranchise {
        return ListHolidaysFranchise(
            franchiseId = franchiseId,
            franchiseName = franchiseName,
            poc = FranchisePoc(
                name = pocName,
                contact = contact
            ),
            address = FranchiseAddress(
                gpsAddress = gpsAddress,
                city = city,
                state = state
            )
        )
    }

}
*/
