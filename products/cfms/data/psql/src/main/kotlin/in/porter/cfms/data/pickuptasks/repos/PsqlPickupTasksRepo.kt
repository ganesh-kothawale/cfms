package `in`.porter.cfms.data.pickuptasks.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.pickuptasks.PickupTasksQueries
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksMapper
import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.cfms.domain.pickuptasks.repos.PickupTasksRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.inject.Inject

class PsqlPickupTasksRepo @Inject constructor(
    private val queries: PickupTasksQueries,
    private val pickupTasksMapper: PickupTasksMapper,
) : Traceable, PickupTasksRepo {
    private val logger = LoggerFactory.getLogger(PsqlPickupTasksRepo::class.java)

    override suspend fun findAllPickupTasks(page: Int, size: Int): List<PickupTask> =
        trace("findAllPickupTasks") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving tasks with page: $page, size: $size")
                val offset = (page - 1) * size
                val records = queries.findAll(size, offset)
                logger.info("Retrieved ${records.size} tasks")
                records.map { record: HlpWithOrdersRecord ->
                    logger.info("Mapping record: $record")
                    pickupTasksMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving tasks: ${e.message}", e)
                throw CfmsException("Failed to retrieve tasks: ${e.message}")
            }
        }

    override suspend fun countAllPickupTasks(): Int =
        trace("countAllPickupTasks") {
            try {
                logger.info("Counting all tasks")
                queries.countAll()
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count tasks: ${e.message}")
            }
        }

    override suspend fun findPickupTasksByIds(taskIds: List<Int>): List<PickupTask> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStatusForPickupTasks(taskIds: List<Int>, status: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getPickupDetailsIdByTaskId(taskId: String): String? {
        return trace("getPickupDetailsIdByTaskId") {
            try {
                queries.findPickupDetailsIdByTaskId(taskId)
            } catch (e: Exception) {
                logger.error("Error fetching pickup details ID for taskId $taskId: ${e.message}", e)
                throw CfmsException("Error fetching pickup details ID for taskId $taskId: ${e.message}")
            }
        }
    }


    override suspend fun updateOrderImageByTaskId(taskId: String, orderImage: List<UUID>) {
        trace("updateOrderImageByTaskId") {
            try {
                queries.updateOrderImageByTaskId(taskId, orderImage)
            } catch (e: Exception) {
                logger.error("Error creating pickup image mapping for taskId $taskId: ${e.message}", e)
                throw CfmsException("Error creating pickup image mapping for taskId $taskId: ${e.message}")
            }
        }
    }

    // Update task status and no_of_packages_received in the tasks table
    override suspend fun updateTaskStatus(taskId: String, noOfPackagesReceived: Int?, taskStatus: String) {
        trace("updateTaskStatus") {
            try {
                queries.updateTaskStatus(taskId, noOfPackagesReceived, taskStatus)
            } catch (e: Exception) {
                logger.error("Error updating task status for taskId $taskId: ${e.message}", e)
                throw CfmsException("Error updating task status for taskId $taskId: ${e.message}")
            }
        }
    }

    // Update order statuses in the orders_test table
    override suspend fun updateOrderStatuses(orders: List<Pair<String, String>>) {
        trace("updateOrderStatuses") {
            try {
                orders.forEach { (orderId, status) ->
                    queries.updateOrderStatus(orderId, status)
                }
            } catch (e: Exception) {
                logger.error("Error updating order statuses: ${e.message}", e)
                throw CfmsException("Error updating order statuses: ${e.message}")
            }
        }
    }
}