package `in`.porter.cfms.domain.holidays.repos

import `in`.porter.cfms.domain.holidays.entities.Holiday
import java.time.LocalDate

interface HolidayRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): Holiday?

    suspend fun record(request: Holiday) : Long

    suspend fun get(franchiseId: String): List<Holiday>

    suspend fun getAllByDate(date: LocalDate): List<Holiday>
}
