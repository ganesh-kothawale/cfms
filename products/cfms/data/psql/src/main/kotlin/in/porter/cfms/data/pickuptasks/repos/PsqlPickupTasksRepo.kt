package `in`.porter.cfms.data.pickuptasks.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.pickuptasks.PickupTasksQueries
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksMapper
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksRowMapper
import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord
import `in`.porter.cfms.domain.pickuptasks.PickupTasksRepo
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
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

}