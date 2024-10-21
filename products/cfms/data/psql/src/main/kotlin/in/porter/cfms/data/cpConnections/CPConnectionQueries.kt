package `in`.porter.cfms.data.cpConnections

import `in`.porter.cfms.data.cpConnections.mappers.CPConnectionRecordMapper
import `in`.porter.cfms.data.cpConnections.mappers.CPConnectionRowMapper
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecord
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecordData
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import java.time.Instant
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

    suspend fun getByPagination(size: Int, offset: Int, franchiseId: String?): List<CpConnectionRecord> = transact {
        if (franchiseId != null) {
            CpConnectionTable.select { CpConnectionTable.franchiseId eq franchiseId }
                .orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
                .limit(size, offset)
        } else {
            CpConnectionTable.selectAll()
                .orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
                .limit(size, offset)
        }
            .map { rowMapper.toRecord(it) }
    }

    suspend fun getCpCount(): Int = transact {
        CpConnectionTable.selectAll().count()
    }

}
