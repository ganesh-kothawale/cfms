package `in`.porter.cfms.data.hlp

import `in`.porter.cfms.data.franchise.records.ListFranchisesRecord
import `in`.porter.cfms.data.hlp.mappers.HlpRowMapper
import `in`.porter.cfms.data.hlp.records.HlpRecord
import `in`.porter.cfms.data.hlp.records.HlpRecordData
import `in`.porter.cfms.data.hlp.records.UpdateHlpRecord
import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsRequest
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.*
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject

class HlpQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val rowMapper: HlpRowMapper,
) : ExposedRepo {
    suspend fun save(req: HlpRecordData): Unit = transact {
        val now = Instant.now()
        HlpsTable.insert {
            it[hlpOrderId] = req.hlpOrderId
            it[hlpOrderStatus] = req.hlpOrderStatus
            it[otp] = req.otp
            it[riderName] = req.riderName
            it[riderNumber] = req.riderNumber
            it[vehicleType] = req.vehicleType
            it[franchiseId] = req.franchiseId
            it[createdAt] = now
            it[updatedAt] = now
        }
    }

    suspend fun update(req: UpdateHlpRecord): Int = transact {
        HlpsTable.update({ HlpsTable.hlpOrderId eq req.hlpOrderId }) {
            it[hlpOrderStatus] = req?.hlpOrderStatus
            it[otp] = req?.otp
            it[riderName] = req?.riderName
            it[riderNumber] = req?.riderNumber
            it[vehicleType] = req?.vehicleType
            it[updatedAt] = Instant.now()
        }
    }

    suspend fun getByHlpOrderId(hlpOrderId: String): HlpRecord? = transact {
        HlpsTable
            .select { HlpsTable.hlpOrderId eq hlpOrderId }
            .map { rowMapper.toRecord(it) }
            .firstOrNull()
    }

    suspend fun findAll(req: FetchHlpRecordsRequest, offset: Int): List<HlpRecord> = transact {
        HlpsTable
            .selectAll()
            .apply {
                req.createdDate?.let { andWhere { HlpsTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }
                req.updatedDate?.let { andWhere { HlpsTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }

                req.driverNames?.let { names ->
                    andWhere {
                        names.map { name -> HlpsTable.riderName like "%$name%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                req.driverNumber?.let { andWhere { HlpsTable.riderNumber eq it } }
                req.hlpOrderIds?.let { ids ->
                    andWhere {
                        ids.map { id -> HlpsTable.hlpOrderId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                req.hlpOrderStatus?.let { andWhere { HlpsTable.hlpOrderStatus eq it } }
                req.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> HlpsTable.franchiseId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                req.vehicleTypes?.let { types ->
                    andWhere {
                        types.map { type -> HlpsTable.vehicleType like "%$type%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
            }
            .orderBy(HlpsTable.createdAt, SortOrder.DESC)
            .limit(req.size, offset)
            .map { rowMapper.toRecord(it) }
    }

    suspend fun countAll(req: FetchHlpRecordsRequest): Int = transact {
        HlpsTable.selectAll()
            .apply {
                req.createdDate?.let { andWhere { HlpsTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }
                req.updatedDate?.let { andWhere { HlpsTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() } }

                req.driverNames?.let { names ->
                    andWhere {
                        names.map { name -> HlpsTable.riderName like "%$name%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                req.driverNumber?.let { andWhere { HlpsTable.riderNumber eq it } }
                req.hlpOrderIds?.let { ids ->
                    andWhere {
                        ids.map { id -> HlpsTable.hlpOrderId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                req.hlpOrderStatus?.let { andWhere { HlpsTable.hlpOrderStatus eq it } }
                req.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> HlpsTable.franchiseId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                req.vehicleTypes?.let { types ->
                    andWhere {
                        types.map { type -> HlpsTable.vehicleType like "%$type%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
            }
            .count()
    }

}
