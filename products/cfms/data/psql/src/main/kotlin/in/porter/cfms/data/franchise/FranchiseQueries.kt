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
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import `in`.porter.cfms.data.franchise.records.UpdateFranchiseRecord
import `in`.porter.cfms.domain.franchise.entities.DomainListFranchisesRequest
import org.jetbrains.exposed.sql.*
import java.time.Instant
import java.time.ZoneOffset
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
    suspend fun save(req: FranchiseRecordData): String = transact {
        val now = Instant.now()
        val franchiseId = FranchisesTable.insert {
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
        } get FranchisesTable.franchiseId

        franchiseId
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

    suspend fun findAll(request: DomainListFranchisesRequest, offset: Int): List<ListFranchisesRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching franchises with request: $request, offset: $offset")

        FranchisesTable.selectAll().apply {
            // Franchise IDs with `or` condition for partial matching
            request.franchiseIds?.let { ids ->
                andWhere {
                    ids.map { id -> FranchisesTable.franchiseId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.pocPrimaryNumber?.let { andWhere { FranchisesTable.primaryNumber eq it } }
            request.emailId?.let { andWhere { FranchisesTable.email eq it } }

            // Porter Hub Names with `or` condition for partial matching
            request.porterHubNames?.let { names ->
                andWhere {
                    names.map { name -> FranchisesTable.porterHubName like "%$name%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.status?.let { andWhere { FranchisesTable.status eq it } }

            // KAMs with `or` condition for partial matching
            request.kams?.let { kams ->
                andWhere {
                    kams.map { kam -> FranchisesTable.kamUser like "%$kam%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Cities with `or` condition for partial matching
            request.cities?.let { cities ->
                andWhere {
                    cities.map { city -> FranchisesTable.city like "%$city%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // States with `or` condition for partial matching
            request.states?.let { states ->
                andWhere {
                    states.map { state -> FranchisesTable.state like "%$state%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.pincode?.let { andWhere { FranchisesTable.pincode eq it.toString() } }
            request.radiusCoverage?.let { andWhere { FranchisesTable.radiusCoverage eq it.toBigDecimal() } }

            // Apply the created_at filter
            request.createdDate?.let {
                andWhere { FranchisesTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
            request.updatedDate?.let {
                andWhere { FranchisesTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }

            // Sort by created_at in descending order
        }.orderBy(FranchisesTable.createdAt, SortOrder.DESC) // Sort by created_at, latest first
            .limit(request.size, offset)
            .map { row ->
                logger.info("Mapping row: $row")
                listMapper.toRecord(row)
            }
    }

    suspend fun countAll(request: DomainListFranchisesRequest): Int = transact {
        addLogger(StdOutSqlLogger)
        FranchisesTable.selectAll().apply {
            request.franchiseIds?.let { ids ->
                andWhere {
                    ids.map { id -> FranchisesTable.franchiseId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.pocPrimaryNumber?.let { andWhere { FranchisesTable.primaryNumber eq it } }
            request.emailId?.let { andWhere { FranchisesTable.email eq it } }

            request.porterHubNames?.let { names ->
                andWhere {
                    names.map { name -> FranchisesTable.porterHubName like "%$name%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.status?.let { andWhere { FranchisesTable.status eq it } }

            request.kams?.let { kams ->
                andWhere {
                    kams.map { kam -> FranchisesTable.kamUser like "%$kam%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.cities?.let { cities ->
                andWhere {
                    cities.map { city -> FranchisesTable.city like "%$city%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.states?.let { states ->
                andWhere {
                    states.map { state -> FranchisesTable.state like "%$state%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.pincode?.let { andWhere { FranchisesTable.pincode eq it.toString() } }
            request.radiusCoverage?.let { andWhere { FranchisesTable.radiusCoverage eq it.toBigDecimal() } }

            request.createdDate?.let {
                andWhere { FranchisesTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
            request.updatedDate?.let {
                andWhere { FranchisesTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
        }.count().toInt()
    }

    suspend fun updateFranchise(record: UpdateFranchiseRecord): Int = transact {
        FranchisesTable.update({ FranchisesTable.franchiseId eq record.franchiseId }) { statement ->
            record.data.pocName.let { statement[FranchisesTable.pocName] = it }
            record.data.primaryNumber.let { statement[FranchisesTable.primaryNumber] = it }
            record.data.email.let { statement[FranchisesTable.email] = it }
            record.data.address.let { statement[FranchisesTable.address] = it }
            record.data.latitude.let { statement[FranchisesTable.latitude] = it }
            record.data.longitude.let { statement[FranchisesTable.longitude] = it }
            record.data.city.let { statement[FranchisesTable.city] = it }
            record.data.state.let { statement[FranchisesTable.state] = it }
            record.data.pincode.let { statement[FranchisesTable.pincode] = it }
            record.data.porterHubName?.let { statement[FranchisesTable.porterHubName] = it }
            record.data.franchiseGst?.let { statement[FranchisesTable.franchiseGst] = it }
            record.data.franchisePan?.let { statement[FranchisesTable.franchisePan] = it }
            record.data.franchiseCanceledCheque?.let { statement[FranchisesTable.franchiseCanceledCheque] = it }
            record.data.status.let { statement[FranchisesTable.status] = it }
            record.data.teamId?.let { statement[FranchisesTable.teamId] = it }
            record.data.daysOfOperation?.let { statement[FranchisesTable.daysOfOperation] = it }
            record.data.startTime.let { statement[FranchisesTable.startTime] = it }
            record.data.endTime.let { statement[FranchisesTable.endTime] = it }
            record.data.cutOffTime.let { statement[FranchisesTable.cutOffTime] = it }
            record.data.hlpEnabled.let { statement[FranchisesTable.hlpEnabled] = it }
            record.data.radiusCoverage.let { statement[FranchisesTable.radiusCoverage] = it }
            record.data.showCrNumber.let { statement[FranchisesTable.showCrNumber] = it }
            record.data.kamUser?.let { statement[FranchisesTable.kamUser] = it }
            record.data.isActive.let { statement[FranchisesTable.isActive] = it }
            record.data.daysOfTheWeek?.let { statement[FranchisesTable.daysOfTheWeek] = it }
            record.data.courierPartners.let { statement[FranchisesTable.courierPartners] = it.joinToString(",") }
            statement[FranchisesTable.updatedAt] = Instant.now()
        }
    }
}
