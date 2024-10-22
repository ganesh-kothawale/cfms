package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksRowMapper
import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PickupTasksQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val pickupTasksRowMapper: PickupTasksRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(PickupTasksQueries::class.java)
    suspend fun findAll(size: Int, offset: Int): Pair<List<HlpWithOrdersRecord>, Int> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Retrieving pickup-tasks with size: $size, offset: $offset")

        // First, fetch the total count of unique hlpOrderIds
        val totalRecords = PickupTasksTable
            .innerJoin(HlpsTable, { PickupTasksTable.hlpId }, { HlpsTable.hlpOrderId })
            .select { HlpsTable.hlpOrderId.isNotNull() }
            .distinct() // Ensure we count only unique hlpOrderIds
            .count() // Get the count of unique hlpOrderIds

        // Fetch and map all rows using the row mapper with limit and offset
        val results = PickupTasksTable
            .innerJoin(HlpsTable, { PickupTasksTable.hlpId }, { HlpsTable.hlpOrderId })
            .innerJoin(OrdersTable, { PickupTasksTable.orderId }, { OrdersTable.orderId })
            .selectAll()
            .limit(size, offset)
            .map { row ->
                logger.info("Mapping row: $row")
                pickupTasksRowMapper.toRecord(row) // Map the row to a PickupOrderRecord object
            }

        // Group the results by `hlpOrderId` and collect all `pickupOrders` for each group
        val groupedResults = results.groupBy { it.hlpOrderId }.map { (hlpOrderId, groupedOrders) ->
            val firstRecord = groupedOrders.first()
            HlpWithOrdersRecord(
                taskId = firstRecord.taskId,
                hlpOrderId = hlpOrderId,
                riderName = firstRecord.riderName,
                riderNumber = firstRecord.riderNumber,
                vehicleType = firstRecord.vehicleType,
                // Collect all PickupOrderRecords from the grouped orders
                pickupOrders = groupedOrders.flatMap { it.pickupOrders }
            )
        }

        // Return both the grouped results and the total count
        return@transact Pair(groupedResults, totalRecords)
    }



    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all pickup-tasks")
        PickupTasksTable
            .selectAll()
            .count()
            .toInt()
    }

}