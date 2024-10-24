package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.orders.mappers.OrderDetailsMapper
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksRowMapper
import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord
import `in`.porter.cfms.data.pickuptasks.records.PickupImageMappingRecord
import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.cfms.data.tasks.mappers.TaskRowMapper
import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.slf4j.LoggerFactory
import java.time.Instant
import javax.inject.Inject

class PickupTasksQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val pickupTasksRowMapper: PickupTasksRowMapper,
    private val taskRowMapper: TaskRowMapper,
    private val orderMapper: OrderDetailsMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(PickupTasksQueries::class.java)
    suspend fun findAll(size: Int, offset: Int): List<HlpWithOrdersRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Retrieving pickup-tasks with size: $size, offset: $offset")

        // Step 1: Fetch all data without applying limit yet
        val results = PickupTasksTable
            .innerJoin(HlpsTable, { PickupTasksTable.hlpId }, { HlpsTable.hlpOrderId })
            .innerJoin(OrdersTable, { PickupTasksTable.orderId }, { OrdersTable.orderId })
            .selectAll()
            .map { row ->
                logger.info("Mapping row: $row")
                pickupTasksRowMapper.toRecord(row)
            }

        // Step 2: Group the results by `hlpOrderId`
        val groupedResults = results.groupBy { it.hlpOrderId }.map { (hlpOrderId, groupedOrders) ->
            val firstRecord = groupedOrders.first()
            HlpWithOrdersRecord(
                taskId = firstRecord.taskId,
                hlpOrderId = hlpOrderId,
                riderName = firstRecord.riderName,
                riderNumber = firstRecord.riderNumber,
                vehicleType = firstRecord.vehicleType,
                pickupOrders = groupedOrders.flatMap { it.pickupOrders }
            )
        }

        return@transact groupedResults.drop(offset).take(size)
    }

    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all pickup-tasks")
        PickupTasksTable
            .selectAll()
            .count()
            .toInt()
    }

    suspend fun findPickupDetailsIdByTaskId(taskId: String): String? = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching pickup details ID for taskId: $taskId")
        PickupTasksTable
            .select { PickupTasksTable.taskId eq taskId }
            .singleOrNull()?.get(PickupTasksTable.pickupTaskId)
    }

    suspend fun insertPickupImageMapping(record: PickupImageMappingRecord) = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Inserting pickup image mapping for taskID: ${record.taskId}, pickupDetailsId: ${record.pickupDetailsId}")
        PickupImageMappingTable.insert {
            it[taskId] = record.taskId
            it[pickupDetailsId] = record.pickupDetailsId
            it[orderImage] = record.orderImage
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
        }
    }

    suspend fun updateTaskStatus(taskId: String, noOfPackagesReceived: Int?, taskStatus: String) = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Updating task status for taskId: $taskId")
        TasksTable.update({ TasksTable.taskId eq taskId }) {
            it[packageReceived] = noOfPackagesReceived
            it[status] = taskStatus
            it[updatedAt] = Instant.now()
        }
    }

    suspend fun updateOrderStatus(orderId: String, status: String) = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Updating order status for orderId: $orderId")
        OrdersTable.update({ OrdersTable.orderId eq orderId }) {
            it[orderStatus] = status
            it[updatedAt] = Instant.now()
        }
    }

}