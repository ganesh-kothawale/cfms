package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.orders.mappers.OrderDetailsMapper
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksRowMapper
import `in`.porter.cfms.data.pickuptasks.pickupimagemappings.PickupOrderMappingsTable
import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord

import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.cfms.data.tasks.mappers.TaskRowMapper
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.UUID
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
            .innerJoin(
                PickupOrderMappingsTable,
                { PickupTasksTable.pickupTaskId },
                { PickupOrderMappingsTable.pickupTaskId })
            .innerJoin(OrdersTable, { PickupOrderMappingsTable.orderId }, { OrdersTable.orderId })
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
            .innerJoin(HlpsTable, { PickupTasksTable.hlpId }, { HlpsTable.hlpOrderId })
            .innerJoin(
                PickupOrderMappingsTable,
                { PickupTasksTable.pickupTaskId },
                { PickupOrderMappingsTable.pickupTaskId })
            .innerJoin(OrdersTable, { PickupOrderMappingsTable.orderId }, { OrdersTable.orderId })
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


    suspend fun updateByTaskId(taskId: String, orderImage: List<UUID>, noPackageReceived: Int?) = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Inserting order image against taskId: $taskId")
        PickupTasksTable.update({ PickupTasksTable.taskId eq taskId }) {
            it[orderImages] = orderImage.joinToString(",")
            it[packageReceived] = noPackageReceived
            it[updatedAt] = Instant.now()
        }
    }

    suspend fun updateOrderStatuses(orders: List<Pair<String, String>>) = transact {
        addLogger(StdOutSqlLogger)
        try {
            //TODO: Bulk Status Update support need to be added
            logger.info("Starting update of order statuses for ${orders.size} orders")

            orders.forEach { (orderId, status) ->
                OrdersTable.update({ OrdersTable.orderId eq orderId }) {
                    it[orderStatus] = status
                    it[updatedAt] = Instant.now()
                }
            }
            logger.info("Successfully updated statuses for ${orders.size} orders")
            return@transact orders.size
        } catch (e: Exception) {
            logger.error("Error during update of order statuses: ${e.message}", e)
            throw CfmsException("Error during update of order statuses: ${e.message}")
        }
    }
}

