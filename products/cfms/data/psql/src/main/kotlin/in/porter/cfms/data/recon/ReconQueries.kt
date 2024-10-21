package `in`.porter.cfms.data.recon

import `in`.porter.cfms.data.recon.mappers.ReconRowMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import java.time.Instant
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
    suspend fun findAll(size: Int, offset: Int): List<ReconRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching recon records with size: $size and offset: $offset")
        ReconTable
            .selectAll()
            .limit(size, offset)
            .map { row ->
                logger.info("Mapping row: $row")
                reconRowMapper.toRecord(row)
            }
    }

    // Count total number of recon records in the database
    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all recon records")
        ReconTable
            .selectAll()
            .count()
            .toInt()
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

}
