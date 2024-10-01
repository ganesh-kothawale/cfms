package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
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

    suspend fun record(req: HolidayRecord): Long = transact {
        // Insert the holiday and return the generated ID
        if (req.franchiseId == "") {
            throw Exception("Franchise ID can not be null or empty.")
        }
        if (req.startDate.isAfter(req.endDate)) {
            throw IllegalArgumentException("Start date cannot be after end date.")
        }
        HolidayTable.insertAndGetId {
            it[franchiseId] = req.franchiseId
            it[startDate] = req.startDate
            it[endDate] = req.endDate
            it[holidayName] = req.holidayName
            it[leaveType] = req.leaveType.name
            it[backupFranchiseIds] = req.backupFranchiseIds
            it[createdAt] = req.createdAt
            it[updatedAt] = req.updatedAt
        }.value.toLong()  // Return the generated ID
    }

    suspend fun getHolidayById(id: Int?): UpdateHolidayRecord? = transact {
        HolidayTable.select {
            HolidayTable.id eq id
        }.firstOrNull()?.let { updateMapper.toRecord(it) }
    }

    // Update holiday by ID
    suspend fun updateHoliday(record: UpdateHolidayRecord) = transact {
        HolidayTable.update({ HolidayTable.id eq record.id }) {
            it[startDate] = record.startDate
            it[endDate] = record.endDate
            it[holidayName] = record.holidayName
            it[leaveType] = record.leaveType.toString()
            it[backupFranchiseIds] = record.backupFranchiseIds
            it[updatedAt] = Instant.now() // Assuming `updatedAt` is updated on each modification
        }
    }

    suspend fun deleteHolidayById(holidayId: Int) = transact {
        HolidayTable.deleteWhere { HolidayTable.id eq holidayId }
    }
}
