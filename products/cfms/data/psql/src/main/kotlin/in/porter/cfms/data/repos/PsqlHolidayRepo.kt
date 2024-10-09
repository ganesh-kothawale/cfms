package `in`.porter.cfms.data.repos

import `in`.porter.cfms.data.holidays.HolidayQueries
import `in`.porter.cfms.data.holidays.mappers.HolidayMapper
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import java.time.LocalDate
import javax.inject.Inject

class PsqlHolidayRepo
@Inject
constructor(
    private val queries: HolidayQueries,
    private val mapper: HolidayMapper
) : Traceable, HolidayRepo {

    override suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): Holiday? =
        trace("getByIdAndDate") {
            queries.getByIdAndDate(franchiseId, startDate, endDate)
                ?.let { mapper.toDomain(it) }
        }

    override suspend fun record(request: Holiday): Long {
        return trace("record") {
            mapper.toRecord(request)
                .let { queries.record(it) }  // Update the `queries.record` to return the ID
        }
    }

    override suspend fun get(franchiseId: String): List<Holiday> =
        trace("get") {
            queries.get(franchiseId)
                .map { mapper.toDomain(it) }
        }

    override suspend fun getAllByDate(date: LocalDate): List<Holiday> =
        trace("getAllByDate") {
            queries.getAllByDate(date)
                .map { mapper.toDomain(it) }
        }
}
