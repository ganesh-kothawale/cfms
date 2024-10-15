package `in`.porter.cfms.data.tasks.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.tasks.TasksQueries
import `in`.porter.cfms.data.tasks.mappers.ListTasksMapper
import `in`.porter.cfms.data.tasks.records.ListTasksRecord
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlTasksRepo
@Inject constructor(
    private val queries: TasksQueries,
    private val listTasksMapper: ListTasksMapper
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
}
