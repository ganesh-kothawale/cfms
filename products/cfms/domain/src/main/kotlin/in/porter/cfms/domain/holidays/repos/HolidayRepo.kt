package `in`.porter.cfms.domain.holidays.repos

import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.UpdateHolidayEntity
import java.time.LocalDate

interface HolidayRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): Holiday?

    suspend fun record(request: Holiday) : Long

    suspend fun update(request: UpdateHolidayEntity) : Long

    suspend fun getById(holidayId: Long): UpdateHolidayEntity?
}
