package `in`.porter.cfms.data.recon

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.recon.ReconTable.innerJoin
import `in`.porter.cfms.data.recon.mappers.ReconRowMapper
import `in`.porter.cfms.data.recon.mappers.ReconTaskRowMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.data.recon.records.ReconTaskRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ReconQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val reconRowMapper: ReconRowMapper,
    private val reconTaskRowMapper: ReconTaskRowMapper
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

    suspend fun fetchReconTasks(size: Int, offset: Int): List<ReconTaskRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching recon tasks with size: $size and offset: $offset")

        val query = ReconTable
            .innerJoin(OrdersTable, { ReconTable.orderId }, { OrdersTable.orderId })
            .innerJoin(PickupTasksTable, { ReconTable.taskId }, { PickupTasksTable.taskId })
            .slice(
                ReconTable.taskId,
                OrdersTable.orderNumber,
                OrdersTable.courierPartner,
                PickupTasksTable.orderImages,
                OrdersTable.awbNumber,
                ReconTable.reconStatus
            )
            .selectAll()
            .limit(size, offset)

        query.map { row ->
            reconTaskRowMapper.toRecord(row)
        }
    }


}
