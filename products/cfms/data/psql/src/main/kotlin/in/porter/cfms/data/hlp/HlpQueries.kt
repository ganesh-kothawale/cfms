package `in`.porter.cfms.data.hlp

import `in`.porter.cfms.data.hlp.records.HlpRecordData
import `in`.porter.cfms.data.hlp.records.UpdateHlpRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import java.time.Instant
import javax.inject.Inject

class HlpQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
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
}
