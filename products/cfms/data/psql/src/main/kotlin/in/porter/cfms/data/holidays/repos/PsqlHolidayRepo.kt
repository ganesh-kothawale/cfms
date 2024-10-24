package `in`.porter.cfms.data.holidays.repos

import `in`.porter.cfms.data.holidays.HolidayQueries
import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.data.holidays.mappers.HolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHoliday
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysFranchise
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class PsqlHolidayRepo
@Inject
constructor(
    private val queries: HolidayQueries,
    private val mapper: HolidayMapper,
    private val listMapper : ListHolidayMapper,
    private val franchiseMapper: ListHolidaysFranchiseRowMapper
) : Traceable, HolidayRepo {

    private val logger = LoggerFactory.getLogger(PsqlHolidayRepo::class.java)

    override suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): Holiday? =
        trace("getByIdAndDate") {
            queries.getByIdAndDate(franchiseId, startDate, endDate)
                ?.let { mapper.toDomain(it) }
        }

    override suspend fun record(request: Holiday): String {
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

    override suspend fun getById(holidayId: String): Holiday? {
        // Fetch the holiday by ID from the database and map it to the domain entity
        return trace("getById") {
            queries.getHolidayById(holidayId)
                ?.let { mapper.toDomain(it) }
        }
    }

    override suspend fun deleteById(holidayId: String) {
        trace("deleteById") {
            queries.deleteHoliday(holidayId)
        }
    }

    override suspend fun update(holiday: Holiday): String {
        // Map the UpdateHolidayEntity to the database model and update the holiday
        return trace("update") {
            mapper.toRecord(holiday)
                .let { queries.updateHoliday(it) }  // Ensure queries.updateHoliday returns the updated holiday ID
        }
    }

    override suspend fun findHolidays(
        franchiseId: String?,
        leaveType: LeaveType?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        page: Int,
        size: Int
    ): List<ListHoliday> {
        logger.info("Received request to fetch holidays from the DB")

        return queries.findHolidays(franchiseId, leaveType, startDate, endDate, page, size)
            .map { row ->
                // Extract franchiseId from the row
                val franchiseIdFromRow = row[HolidayTable.franchiseId]

                // Find the corresponding franchise by ID
                val franchiseRow = queries.findFranchiseById(franchiseIdFromRow)

                // Map the ResultRow to ListHolidaysFranchise
                val franchise = franchiseRow?.let { franchiseMapper.toRecord(it) }

                // Map the holiday row to the ListHoliday domain object and copy franchise
                listMapper.toRecord(row).copy(
                    franchise = franchise ?: throw Exception("Franchise not found")
                )
            }
    }



    override suspend fun countHolidays(
        franchiseId: String?,
        leaveType: LeaveType?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Int {
        logger.info("Received request to count total number of holidays")
        return queries.countHolidays(franchiseId, leaveType, startDate, endDate)
    }

    // Query to find a franchise by its ID
    override suspend fun findFranchiseById(franchiseId: String): ListHolidaysFranchise? {
        return trace("findFranchiseById") {
            // Fetch the franchise by ID from the database
            val resultRow = queries.findFranchiseById(franchiseId)

            // If resultRow is not null, map it to ListHolidaysFranchise
            resultRow?.let { row ->
                franchiseMapper.toRecord(row)
            }
        }
    }
}
