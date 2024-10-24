package `in`.porter.cfms.domain.holidays.repos

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import java.time.LocalDate

interface HolidayRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): Holiday?

    suspend fun record(request: Holiday) : String

    suspend fun get(franchiseId: String): List<Holiday>

    suspend fun getAllByDate(date: LocalDate): List<Holiday>

    suspend fun update(request: Holiday) : String

    suspend fun getById(holidayId: String): Holiday?

    suspend fun deleteById(holidayId: String)

    suspend fun findHolidays(
        franchiseId: String?,
        leaveType: LeaveType?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        page: Int,
        size: Int
    ): List<ListHoliday>

    suspend fun countHolidays(
        franchiseId: String?,
        leaveType: LeaveType?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Int

    suspend fun findFranchiseById(franchiseId: String): ListHolidaysFranchise?
}
