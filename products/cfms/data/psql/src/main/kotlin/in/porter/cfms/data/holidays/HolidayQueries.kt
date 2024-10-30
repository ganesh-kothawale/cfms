package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.time.LocalDate
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

    suspend fun findHolidays(
        franchiseId: String?,
        leaveType: LeaveType?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        page: Int,
        size: Int
    ): List<ResultRow> {
        return transact {
            addLogger(StdOutSqlLogger)
            (HolidayTable innerJoin FranchisesTable)
            .selectAll()
                .apply {
                    // Only add franchiseId filter if it's not null
                    if (franchiseId != null) {
                        andWhere { HolidayTable.franchiseId eq franchiseId }
                    }
                    // Apply other filters if needed
                    if (leaveType != null) {
                        andWhere { HolidayTable.leaveType eq leaveType.name }
                    }
                    if (startDate != null) {
                        andWhere { HolidayTable.startDate greaterEq startDate }
                    }
                    if (endDate != null) {
                        andWhere { HolidayTable.endDate lessEq endDate }
                    }
                }
                .limit(size, offset = (page - 1) * size)
                .toList()
        }
    }



    // Query to count the total number of holidays with filters applied
    suspend fun countHolidays(
        franchiseId: String?,
        leaveType: LeaveType?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Int {
        return transact {  // Use transact to ensure transaction context
            addLogger(StdOutSqlLogger)
            HolidayTable
                .selectAll()
                .apply {
                    if (franchiseId != null) {
                        andWhere { HolidayTable.franchiseId eq franchiseId }
                    }
                    if (leaveType != null) {
                        andWhere { HolidayTable.leaveType eq leaveType.name } // Enum to string
                    }
                    if (startDate != null) {
                        andWhere { HolidayTable.startDate greaterEq startDate }
                    }
                    if (endDate != null) {
                        andWhere { HolidayTable.endDate lessEq endDate }
                    }
                }
                .count()
        }
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
