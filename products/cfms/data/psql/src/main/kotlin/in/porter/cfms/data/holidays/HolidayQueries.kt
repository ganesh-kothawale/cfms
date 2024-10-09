package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.insert
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

class HolidayQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val mapper: HolidayRowMapper,
    private val updateMapper: UpdateHolidayRowMapper
) : ExposedRepo {

    suspend fun getByIdAndDate(franchiseId: String, startDate: LocalDate, endDate: LocalDate) = transact {
        HolidayTable.select {
            (HolidayTable.franchiseId eq franchiseId) and
                    (HolidayTable.startDate eq startDate) and
                    (HolidayTable.endDate eq endDate)
        }.firstOrNull()
            ?.let { mapper.toRecord(it) }
    }

    suspend fun record(req: HolidayRecord): Int = transact {
        // Insert the holiday and return the generated ID
        if (req.franchiseId == "") {
            throw Exception("Franchise ID can not be null or empty.")
        }
        if (req.startDate.isAfter(req.endDate)) {
            throw IllegalArgumentException("Start date cannot be after end date.")
        }
        val insertedId = HolidayTable.insert {
            it[franchiseId] = req.franchiseId
            it[startDate] = req.startDate
            it[endDate] = req.endDate
            it[holidayName] = req.holidayName
            it[leaveType] = req.leaveType.name
            it[backupFranchiseIds] = req.backupFranchiseIds
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
        } get HolidayTable.holidayId

        insertedId
    }

    suspend fun getHolidayById(id: Int): UpdateHolidayRecord? = transact {
        HolidayTable.select {
            HolidayTable.holidayId eq id
        }.firstOrNull()?.let { updateMapper.toRecord(it) }
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
    suspend fun updateHoliday(record: UpdateHolidayRecord)= transact {
        HolidayTable.update({ HolidayTable.holidayId eq record.holidayId }) {
            it[startDate] = record.startDate
            it[endDate] = record.endDate
            it[holidayName] = record.holidayName
            it[leaveType] = record.leaveType.toString()
            it[backupFranchiseIds] = record.backupFranchiseIds
            it[updatedAt] = Instant.now() // Assuming `updatedAt` is updated on each modification
        }
    }

    suspend fun deleteHoliday(holidayId: Int): Int {
       return transact{
            HolidayTable.deleteWhere { HolidayTable.holidayId eq holidayId }
        }
    }
}
