package `in`.porter.cfms.domain.holidays.repos

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import java.time.LocalDate

interface HolidayRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): UpdateHoliday?

    suspend fun record(request: Holiday) : Int

    suspend fun get(franchiseId: String): List<Holiday>

    suspend fun getAllByDate(date: LocalDate): List<Holiday>

    suspend fun update(request: UpdateHoliday) : Int

    suspend fun getById(holidayId: Int): UpdateHoliday?

    suspend fun deleteById(holidayId: Int)

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
