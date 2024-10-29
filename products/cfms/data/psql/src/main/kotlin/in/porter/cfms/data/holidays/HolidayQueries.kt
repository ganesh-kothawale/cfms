package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.ListHolidaysDomainRequest
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class HolidayQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val mapper: HolidayRowMapper

) : ExposedRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate): HolidayRecord? = transact {
        HolidayTable.select {
            (HolidayTable.franchiseId eq franchiseId) and
                    (HolidayTable.startDate eq startDate) and
                    (HolidayTable.endDate eq endDate)
        }.firstOrNull()
            ?.let { mapper.toRecord(it) }
    }

    suspend fun record(req: HolidayRecord): String = transact {
        // Insert the holiday and return the generated ID
        if (req.franchiseId == "") {
            throw Exception("Franchise ID can not be null or empty.")
        }
        if (req.startDate.isAfter(req.endDate)) {
            throw IllegalArgumentException("Start date cannot be after end date.")
        }
        val insertedId = HolidayTable.insert {
            it[holidayId] = req.holidayId
            it[franchiseId] = req.franchiseId
            it[startDate] = req.startDate
            it[endDate] = req.endDate
            it[holidayName] = req.holidayName
            it[leaveType] = req.leaveType.name
            it[backupFranchiseIds] = req.backupFranchiseIds.joinToString(",")
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
        } get HolidayTable.holidayId

        insertedId
    }

    suspend fun getHolidayById(id: String): HolidayRecord? = transact {
        HolidayTable.select {
            HolidayTable.holidayId eq id
        }.firstOrNull()?.let { mapper.toRecord(it) }
    }

    suspend fun get(franchiseId: String): List<HolidayRecord> = transact {
        HolidayTable.select {
            HolidayTable.franchiseId eq franchiseId
        }.map { mapper.toRecord(it) }  // Map each row to a HolidayRecord
    }

    suspend fun getAllByDate(date: LocalDate): List<HolidayRecord> = transact {
        HolidayTable.select {
            HolidayTable.startDate lessEq date and (HolidayTable.endDate greaterEq date)
        }.map { mapper.toRecord(it) }  // Map each row to a HolidayRecord
    }

    // Update holiday by ID
    suspend fun updateHoliday(record: HolidayRecord):String = transact {
        HolidayTable.update({ HolidayTable.holidayId eq record.holidayId }) {
            it[startDate] = record.startDate
            it[endDate] = record.endDate
            it[holidayName] = record.holidayName
            it[leaveType] = record.leaveType.toString()
            it[backupFranchiseIds] = record.backupFranchiseIds.joinToString(",")
            it[updatedAt] = Instant.now() // Assuming `updatedAt` is updated on each modification
        }.toString()
    }

    suspend fun deleteHoliday(holidayId: String): Int {
        return transact{
            HolidayTable.deleteWhere { HolidayTable.holidayId eq holidayId }
        }
    }

    suspend fun findHolidays(request: ListHolidaysDomainRequest, offset: Int): List<ResultRow> = transact {
        (HolidayTable innerJoin FranchisesTable)
            .selectAll()
            .apply {
                request.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> HolidayTable.franchiseId eq id }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                request.backupFranchises?.let { ids ->
                    andWhere {
                        ids.map { id -> HolidayTable.backupFranchiseIds like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                request.leaveType?.let { andWhere { HolidayTable.leaveType eq it } }

                request.createdDate?.let { andWhere { HolidayTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }
                request.updatedDate?.let { andWhere { HolidayTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }
                request.fromDate?.let { andWhere { HolidayTable.startDate greaterEq it } }
                request.toDate?.let { andWhere { HolidayTable.endDate lessEq it } }
            }
            .orderBy(HolidayTable.createdAt, SortOrder.DESC)
            .limit(request.size, offset)
            .toList()
    }

    suspend fun countHolidays(request: ListHolidaysDomainRequest): Int = transact {
        (HolidayTable innerJoin FranchisesTable)
            .selectAll()
            .apply {
                request.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> HolidayTable.franchiseId eq id }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                request.backupFranchises?.let { ids ->
                    andWhere {
                        ids.map { id -> HolidayTable.backupFranchiseIds like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                request.leaveType?.let { andWhere { HolidayTable.leaveType eq it } }

                request.createdDate?.let { andWhere { HolidayTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }
                request.updatedDate?.let { andWhere { HolidayTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }
                request.fromDate?.let { andWhere { HolidayTable.startDate greaterEq it } }
                request.toDate?.let { andWhere { HolidayTable.endDate lessEq it } }
            }
            .count()
    }


    suspend fun findFranchiseById(franchiseId: String): ResultRow? {
        return transaction {
            addLogger(StdOutSqlLogger)
            FranchisesTable
                .select { FranchisesTable.franchiseId eq franchiseId }
                .singleOrNull()  // Return the first result or null if not found
        }
    }
}
