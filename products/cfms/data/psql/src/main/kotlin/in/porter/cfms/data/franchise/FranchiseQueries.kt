package `in`.porter.cfms.data.franchise

import `in`.porter.cfms.data.franchise.mappers.FranchiseRowMapper
import `in`.porter.cfms.data.franchise.mappers.ListFranchisesRowMapper
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import `in`.porter.cfms.data.franchise.records.ListFranchisesRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import `in`.porter.cfms.data.franchise.records.UpdateFranchiseRecord

import org.jetbrains.exposed.sql.*
import java.time.Instant
import javax.inject.Inject

class FranchiseQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val mapper: FranchiseRowMapper,
    private val listMapper: ListFranchisesRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(FranchiseQueries::class.java)
    suspend fun save(req: FranchiseRecordData): Int = transact {
        val now = Instant.now()
        FranchisesTable.insertAndGetId {
            it[franchiseId] = req.franchiseId
            it[address] = req.address
            it[city] = req.city
            it[state] = req.state
            it[pincode] = req.pincode
            it[pocName] = req.pocName
            it[primaryNumber] = req.primaryNumber
            it[email] = req.email
            it[status] = req.status
            it[porterHubName] = req.porterHubName
            it[franchiseGst] = req.franchiseGst
            it[franchisePan] = req.franchisePan
            it[franchiseCanceledCheque] = req.franchiseCanceledCheque
            it[daysOfOperation] = req.daysOfOperation
            it[hlpEnabled] = req.hlpEnabled
            it[kamUser] = req.kamUser
            it[showCrNumber] = req.showCrNumber
            it[createdAt] = now
            it[updatedAt] = now
            it[cutOffTime] = req.cutOffTime
            it[startTime] = req.startTime
            it[endTime] = req.endTime
            it[longitude] = req.longitude
            it[latitude] = req.latitude
            it[radiusCoverage] = req.radiusCoverage
            it[teamId] = req.teamId ?: 0
        }.value
    }

    suspend fun getByCode(code: String) = transact {
        FranchisesTable.select { FranchisesTable.franchiseId eq code }
            .firstOrNull()
            ?.let { mapper.toRecord(it) }
    }

    suspend fun getByEmail(email: String) = transact {
        FranchisesTable.select { FranchisesTable.email eq email }
            .firstOrNull()
            ?.let { mapper.toRecord(it) }
    }

    suspend fun findAll(size: Int, offset: Int): List<ListFranchisesRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching franchises with size: $size and offset: $offset")
        FranchisesTable
            .selectAll()
            .limit(size, offset)
            .map { row ->
                logger.info("Mapping row: $row")
                listMapper.toRecord(row)

            }
    }

    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all franchises")
        FranchisesTable
            .selectAll()
            .count()
            .toInt()
    }
    suspend fun updateFranchise(record: UpdateFranchiseRecord): Int = transact {
        FranchisesTable.update({ FranchisesTable.franchiseId eq record.franchiseId }) { statement ->
            record.data.pocName?.let { statement[FranchisesTable.pocName] = it }
            record.data.primaryNumber?.let { statement[FranchisesTable.primaryNumber] = it }
            record.data.email?.let { statement[FranchisesTable.email] = it }
            record.data.address?.let { statement[FranchisesTable.address] = it }
            record.data.latitude?.let { statement[FranchisesTable.latitude] = it }
            record.data.longitude?.let { statement[FranchisesTable.longitude] = it }
            record.data.city?.let { statement[FranchisesTable.city] = it }
            record.data.state?.let { statement[FranchisesTable.state] = it }
            record.data.pincode?.let { statement[FranchisesTable.pincode] = it }
            record.data.porterHubName?.let { statement[FranchisesTable.porterHubName] = it }
            record.data.franchiseGst?.let { statement[FranchisesTable.franchiseGst] = it }
            record.data.franchisePan?.let { statement[FranchisesTable.franchisePan] = it }
            record.data.franchiseCanceledCheque?.let { statement[FranchisesTable.franchiseCanceledCheque] = it }
            record.data.status?.let { statement[FranchisesTable.status] = it }
            record.data.teamId?.let { statement[FranchisesTable.teamId] = it }
            record.data.daysOfOperation?.let { statement[FranchisesTable.daysOfOperation] = it }
            record.data.startTime?.let { statement[FranchisesTable.startTime] = it }
            record.data.endTime?.let { statement[FranchisesTable.endTime] = it }
            record.data.cutOffTime?.let { statement[FranchisesTable.cutOffTime] = it }
            record.data.hlpEnabled?.let { statement[FranchisesTable.hlpEnabled] = it }
            record.data.radiusCoverage?.let { statement[FranchisesTable.radiusCoverage] = it }
            record.data.showCrNumber?.let { statement[FranchisesTable.showCrNumber] = it }
            record.data.kamUser?.let { statement[FranchisesTable.kamUser] = it }
            record.data.isActive?.let { statement[FranchisesTable.isActive] = it }
            record.data.daysOfTheWeek?.let { statement[FranchisesTable.daysOfTheWeek] = it }
            record.data.courierPartners?.let { statement[FranchisesTable.courierPartners] = it.joinToString(",") }
            statement[FranchisesTable.updatedAt] = Instant.now()
        }
    }
}
