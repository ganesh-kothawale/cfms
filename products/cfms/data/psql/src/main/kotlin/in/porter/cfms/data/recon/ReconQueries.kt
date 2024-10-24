package `in`.porter.cfms.data.recon

import `in`.porter.cfms.data.recon.mappers.ReconRowMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ReconQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val reconRowMapper: ReconRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(ReconQueries::class.java)

    // Retrieve recon records with pagination
    suspend fun findAll(size: Int, offset: Int, packagingRequired: Boolean?): List<ReconRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching recon records with size: $size and offset: $offset")
        val query = if (packagingRequired != null) {
            ReconTable
                .select { ReconTable.packagingRequired eq packagingRequired }
                .limit(size, offset)
        } else {
            ReconTable
                .selectAll()
                .limit(size, offset)
        }

        query.map { row ->
            logger.info("Mapping row: $row")
            reconRowMapper.toRecord(row)
        }
    }

    // Count total number of recon records in the database
    suspend fun countAll(packagingRequired: Boolean?): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all recon records")
        val query = if (packagingRequired != null) {
            ReconTable
                .select { ReconTable.packagingRequired eq packagingRequired }
        } else {
            ReconTable.selectAll()
        }

        query.count().toInt()
    }

    suspend fun insert(reconRecord: ReconRecord): String = transact {
        logger.info("Inserting a new recon into the database")

        ReconTable.insert { row ->
            row[reconId] = reconRecord.reconId
            row[orderId] = reconRecord.orderId
            row[taskId] = reconRecord.taskId
            row[teamId] = reconRecord.teamId
            row[reconStatus] = reconRecord.reconStatus
            row[packagingRequired] = reconRecord.packagingRequired
            row[prePackagingImageUrl] = reconRecord.prePackagingImageUrl
            row[shipmentIsEnvelopeOrDocument] = reconRecord.shipmentIsEnvelopeOrDocument
            row[shipmentWeight] = reconRecord.shipmentWeight
            row[weightPhotoUrl] = reconRecord.weightPhotoUrl
            row[shipmentDimensionsCmOrInch] = reconRecord.shipmentDimensionsCmOrInch
            row[shipmentLength] = reconRecord.shipmentLength
            row[shipmentWidth] = reconRecord.shipmentWidth
            row[shipmentHeight] = reconRecord.shipmentHeight
            row[dimensionsPhotoUrls] = reconRecord.dimensionsPhotoUrls
            row[returnRequested] = reconRecord.returnRequested
            row[returnImageUrl] = reconRecord.returnImageUrl
            row[createdAt] = reconRecord.createdAt
            row[updatedAt] = reconRecord.updatedAt
        }
        reconRecord.reconId
    }

    suspend fun findByReconId(reconId: String): ReconRecord? = transact {
        ReconTable
            .select { ReconTable.reconId eq reconId }
            .map { reconRowMapper.toRecord(it) }  // Mapping the result row to ReconRecord
            .firstOrNull()
    }

    suspend fun deleteReconById(reconId: String): Unit = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Deleting recon with ID: $reconId")
        ReconTable.deleteWhere { ReconTable.reconId eq reconId }
    }

}
