package `in`.porter.cfms.data.franchise

import `in`.porter.cfms.data.franchise.mappers.FranchiseRowMapper
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import java.time.Instant
import javax.inject.Inject

class FranchiseQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val mapper: FranchiseRowMapper,
) : ExposedRepo {
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
        }.value
    }

    suspend fun getByCode(code: String) = transact {
        FranchisesTable.select { FranchisesTable.franchiseId eq code }
            .firstOrNull()
            ?.let { mapper.toRecord(it) }
    }
}
