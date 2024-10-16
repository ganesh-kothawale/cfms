package `in`.porter.cfms.data.tasks.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.tasks.TasksQueries
import `in`.porter.cfms.data.tasks.mappers.ListTasksMapper
import `in`.porter.cfms.data.tasks.mappers.ListTasksRowMapper
import `in`.porter.cfms.data.tasks.records.ListTasksRecord
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlTasksRepo
@Inject constructor(
    private val queries: TasksQueries,
    private val listTasksMapper: ListTasksMapper,
    private val listTasksRowMapper: ListTasksRowMapper
) : Traceable, TasksRepo {

    private val logger = LoggerFactory.getLogger(PsqlTasksRepo::class.java)

    override suspend fun findAllTasks(page: Int, size: Int): List<ListTasks> =
        trace("findAllTasks") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving tasks with page: $page, size: $size")
                // Calculate the offset based on the page and size
                val offset = (page - 1) * size
                // Perform the query to get the list of task records
                val records = queries.findAll(size, offset)

                logger.info("Retrieved ${records.size} tasks")
                logger.info("Mapping records to domain objects")

                // Map each ListTasksRecord to a Task entity
                records.map { record: ListTasksRecord ->
                    logger.info("Mapping record: $record")
                    listTasksMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving tasks: ${e.message}", e)
                throw CfmsException("Failed to retrieve tasks: ${e.message}")
            }
        }

    override suspend fun countAllTasks(): Int =
        trace("countAllTasks") {
            try {
                logger.info("Counting all tasks")
                queries.countAll()
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count tasks: ${e.message}")
            }
        }

    // New method for finding tasks by IDs
    override suspend fun findTasksByIds(taskIds: List<Int>): List<ListTasks> =
        trace("findTasksByIds") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving tasks by IDs: $taskIds")
                val records = queries.findByIds(taskIds)

                logger.info("Retrieved ${records.size} tasks")
                records.map { record: ListTasksRecord ->
                    logger.info("Mapping record: $record")
                    listTasksMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving tasks by IDs: ${e.message}", e)
                throw CfmsException("Failed to retrieve tasks by IDs: ${e.message}")
            }
        }

    // New method for updating status of tasks by IDs
    override suspend fun updateStatusForTasks(taskIds: List<Int>, status: String) =
        trace("updateStatusForTasks") { _: io.opentracing.Span ->
            try {
                logger.info("Updating status for tasks with IDs: $taskIds")
                queries.updateStatus(taskIds, status)
                logger.info("Successfully updated status for tasks: $taskIds")
            } catch (e: Exception) {
                logger.error("Error occurred while updating status for tasks: ${e.message}", e)
                throw CfmsException("Failed to update task statuses: ${e.message}")
            }
        }
}
