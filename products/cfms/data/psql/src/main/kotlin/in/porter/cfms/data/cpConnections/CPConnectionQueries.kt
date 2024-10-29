package `in`.porter.cfms.data.cpConnections

import `in`.porter.cfms.data.courierPartners.CourierPartnersTable
import `in`.porter.cfms.data.cpConnections.mappers.CPConnectionRecordMapper
import `in`.porter.cfms.data.cpConnections.mappers.CPConnectionRowMapper
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecord
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecordData
import `in`.porter.cfms.domain.cpConnections.entities.FetchCPConnectionsRequest
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject


class CPConnectionQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    val mapper: CPConnectionRecordMapper,
    private val rowMapper: CPConnectionRowMapper,
) : ExposedRepo {
    suspend fun record(req: CpConnectionRecordData) = transact {
        val now = Instant.now()
        CpConnectionTable.insert {
            it[cpId] = req.cpId
            it[franchiseId] = req.franchiseId
            it[manifestImageUrl] = req.manifestImageUrl
            it[createdAt] = now
            it[updatedAt] = now
        }
    }

    suspend fun getByPagination(request: FetchCPConnectionsRequest, offset: Int): List<CpConnectionRecord> = transact {
        CpConnectionTable
            .join(CourierPartnersTable, JoinType.LEFT, CpConnectionTable.cpId, CourierPartnersTable.id)
            .selectAll()
            .apply {
                request.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> CpConnectionTable.franchiseId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.courierPartners?.let { partners ->
                    andWhere {
                        partners.map { partner -> CourierPartnersTable.name eq partner }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.createdDate?.let {
                    andWhere { CpConnectionTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
                request.updatedDate?.let {
                    andWhere { CpConnectionTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
            }
            .orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
            .limit(request.size, offset)
            .map { rowMapper.toRecord(it) }
    }

    /*suspend fun getByPaginationByFranchiseId(size: Int, offset: Int, franchiseId: String): List<CpConnectionRecord> =
        transact {
            CpConnectionTable.select { CpConnectionTable.franchiseId eq franchiseId }
                .orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
                .limit(size, offset)
                .map { rowMapper.toRecord(it) }
        }*/

    suspend fun getCpCount(request: FetchCPConnectionsRequest): Int = transact {
        CpConnectionTable
            .join(CourierPartnersTable, JoinType.LEFT, CpConnectionTable.cpId, CourierPartnersTable.id)
            .selectAll()
            .apply {
                request.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> CpConnectionTable.franchiseId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.courierPartners?.let { partners ->
                    andWhere {
                        partners.map { partner -> CourierPartnersTable.name eq partner }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.createdDate?.let {
                    andWhere { CpConnectionTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
                request.updatedDate?.let {
                    andWhere { CpConnectionTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
            }
            .count()
    }

    /*suspend fun getCpCountByFranchiseId(franchiseId: String): Int = transact {
        CpConnectionTable.select { CpConnectionTable.franchiseId eq franchiseId }.count()
    }*/

}
