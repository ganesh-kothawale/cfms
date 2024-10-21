package `in`.porter.cfms.data.hlp

import `in`.porter.cfms.data.franchise.records.ListFranchisesRecord
import `in`.porter.cfms.data.hlp.mappers.HlpRowMapper
import `in`.porter.cfms.data.hlp.records.HlpRecord
import `in`.porter.cfms.data.hlp.records.HlpRecordData
import `in`.porter.cfms.data.hlp.records.UpdateHlpRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.*
import java.time.Instant
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

    suspend fun update(req: UpdateHlpRecord): Unit = transact {
        HlpsTable.update({ HlpsTable.hlpOrderId eq req.hlpOrderId }) {
            it[hlpOrderStatus] = req?.hlpOrderStatus
            it[otp] = req?.otp
            it[riderName] = req?.riderName
            it[riderNumber] = req?.riderNumber
            it[vehicleType] = req?.vehicleType
            it[updatedAt] = Instant.now()
        }
    }

    suspend fun findAll(size: Int, offset: Int): List<HlpRecord> = transact {
        HlpsTable
            .selectAll()
            .limit(size, offset)
            .map { rowMapper.toRecord(it) }
    }

    suspend fun countAll(): Int = transact {
        HlpsTable
            .selectAll()
            .count()
    }
}
