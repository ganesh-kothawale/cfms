package `in`.porter.cfms.domain.holidays.repos

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import java.time.LocalDate

interface HolidayRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): Holiday?

    suspend fun record(request: Holiday) : Int

    suspend fun get(franchiseId: String): List<Holiday>

    suspend fun getAllByDate(date: LocalDate): List<Holiday>

    suspend fun update(request: UpdateHolidayEntity) : Int

    suspend fun getById(holidayId: Int): UpdateHolidayEntity?
}
