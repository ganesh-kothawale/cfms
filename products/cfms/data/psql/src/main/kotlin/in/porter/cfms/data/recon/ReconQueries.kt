package `in`.porter.cfms.data.recon

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.packageIssue.mappers.PackageIssueRowMapper
import `in`.porter.cfms.data.recon.mappers.ReconRowMapper
import `in`.porter.cfms.data.recon.mappers.ReconTaskRowMapper
import `in`.porter.cfms.data.packageIssue.records.PackageIssueRecord
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.data.recon.records.ReconTaskRecord
import `in`.porter.cfms.domain.packageIssue.entities.DomainListAllPackageIssueRequest
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory
import java.time.ZoneOffset
import javax.inject.Inject

class ReconQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val reconRowMapper: ReconRowMapper,
    private val reconTaskRowMapper: ReconTaskRowMapper,
    private val packageIssueRowMapper: PackageIssueRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(ReconQueries::class.java)

    // Retrieve recon records with pagination
    suspend fun findAll(size: Int, offset: Int): List<ReconRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching recon records with size: $size and offset: $offset")
        val query = ReconTable
            .selectAll()
            .limit(size, offset)
        query.map { row ->
            logger.info("Mapping row: $row")
            reconRowMapper.toRecord(row)
        }
    }

    // Count total number of recon records in the database
    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all recon records")
        val query = ReconTable.selectAll()
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

    suspend fun countAllPackageIssue(request: DomainListAllPackageIssueRequest): Int = transact {
        addLogger(StdOutSqlLogger)
        ReconTable
            .innerJoin(OrdersTable, { ReconTable.orderId }, { OrdersTable.orderId })
            .slice(
                ReconTable.reconId,
                ReconTable.orderId,
                ReconTable.taskId,
                ReconTable.teamId,
                ReconTable.reconStatus,
                ReconTable.returnRequested,
                ReconTable.returnImageUrl,
                ReconTable.action,
                ReconTable.createdAt,
                ReconTable.updatedAt,
                OrdersTable.senderMobile,
                OrdersTable.senderName,
                OrdersTable.franchiseId,
                OrdersTable.awbNumber
            )
            .selectAll()
            .apply {
                request.createdDate?.let {
                    andWhere { ReconTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
                request.updatedDate?.let {
                    andWhere { ReconTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
                request.franchiseId?.let { ids ->
                    andWhere {
                        ids.map { id -> OrdersTable.franchiseId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.orderId?.let { ids ->
                    andWhere {
                        ids.map { id -> OrdersTable.orderId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.awbNo?.let { ids ->
                    andWhere {
                        ids.map { id -> OrdersTable.awbNumber like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }
                request.action?.let {
                    andWhere { ReconTable.action eq it }
                }
                request.returnRequested?.let {
                    andWhere { ReconTable.returnRequested eq it }
                }
            }.count()
    }

    suspend fun findAllPackageIssue(request: DomainListAllPackageIssueRequest, offset: Int): List<PackageIssueRecord> =
        transact {
            addLogger(StdOutSqlLogger)
            logger.info("Fetching package issues with size: ${request.size} and offset: $offset")

            ReconTable
                .innerJoin(OrdersTable, { ReconTable.orderId }, { OrdersTable.orderId })
                .slice(
                    ReconTable.reconId,
                    ReconTable.orderId,
                    ReconTable.taskId,
                    ReconTable.teamId,
                    ReconTable.reconStatus,
                    ReconTable.returnRequested,
                    ReconTable.returnImageUrl,
                    ReconTable.action,
                    ReconTable.createdAt,
                    ReconTable.updatedAt,
                    OrdersTable.senderMobile,
                    OrdersTable.senderName,
                    OrdersTable.franchiseId,
                    OrdersTable.awbNumber
                )
                .selectAll()
                .apply {
                    request.createdDate?.let {
                        andWhere { ReconTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                    }
                    request.updatedDate?.let {
                        andWhere { ReconTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                    }
                    request.franchiseId?.let { ids ->
                        andWhere {
                            ids.map { id -> OrdersTable.franchiseId like "%$id%" }
                                .reduce { acc, condition -> acc or condition }
                        }
                    }
                    request.orderId?.let { ids ->
                        andWhere {
                            ids.map { id -> OrdersTable.orderId like "%$id%" }
                                .reduce { acc, condition -> acc or condition }
                        }
                    }
                    request.awbNo?.let { ids ->
                        andWhere {
                            ids.map { id -> OrdersTable.awbNumber like "%$id%" }
                                .reduce { acc, condition -> acc or condition }
                        }
                    }
                    request.action?.let {
                        andWhere { ReconTable.action eq it }
                    }
                    request.returnRequested?.let {
                        andWhere { ReconTable.returnRequested eq it }
                    }
                }
                .limit(request.size, offset)
                .orderBy(ReconTable.createdAt, SortOrder.DESC)
                .map { row ->
                    packageIssueRowMapper.toRecord(row)
                }
        }
}
