package `in`.porter.cfms.data.tasks.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksMapper
import `in`.porter.cfms.data.recon.mappers.ReconMapper
import `in`.porter.cfms.data.tasks.TasksQueries
import `in`.porter.cfms.data.tasks.mappers.ListTaskMapper
import `in`.porter.cfms.data.tasks.mappers.ListTaskRowMapper
import `in`.porter.cfms.data.tasks.mappers.TaskMapper
import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.cfms.domain.tasks.entities.DomainListTasksRequest
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlTasksRepo
@Inject constructor(
    private val queries: TasksQueries,
    private val taskMapper: TaskMapper,
    private val listTaskMapper: ListTaskMapper,
) : Traceable, TasksRepo {

    private val logger = LoggerFactory.getLogger(PsqlTasksRepo::class.java)

    override suspend fun findAllTasks(request: DomainListTasksRequest): List<ListTasks> =
        trace("findAllTasks") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving tasks with page: ${request.page}, size: ${request.size}")
                val offset = (request.page - 1) * request.size
                val records = queries.findAll(request, offset)

                logger.info("Found ${records.size} tasks")
                logger.info("Mapping records to domain objects")

                records.map { record ->
                    logger.info("Mapping record: $record")
                    listTaskMapper.toDomain(record) // Use ListTaskMapper for direct conversion to ListTasks entity
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving tasks: ${e.message}", e)
                throw CfmsException("Failed to retrieve tasks: ${e.message}")
            }
        }

    override suspend fun countAllTasks(request: DomainListTasksRequest): Int =
        trace("countAllTasks") {
            try {
                logger.info("Counting all tasks")
                queries.countAll(request)
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count tasks: ${e.message}")
            }
        }

    // New method for finding tasks by IDs
    override suspend fun findTasksByIds(taskIds: List<String>): List<Tasks> =
        trace("findTasksByIds") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving tasks by IDs: $taskIds")
                val records = queries.findByIds(taskIds)

                logger.info("Retrieved ${records.size} tasks")
                records.map { record: TaskRecord ->

                    logger.info("Mapping record: $record")
                    taskMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving tasks by IDs: ${e.message}", e)
                throw CfmsException("Failed to retrieve tasks by IDs: ${e.message}")
            }
        }

    // New method for updating status of tasks by IDs
    override suspend fun updateStatusForTasks(taskIds: List<String>, status: String) =
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

    override suspend fun create(task: Tasks): String =
        trace("createTask") {
            try {
                logger.info("Creating a new task in the database")
                // Map the domain task to a record
                taskMapper.toRecord(task)
                    .let { queries.insert(it) }
            } catch (e: Exception) {
                logger.error("Error occurred while creating a task: ${e.message}", e)
                throw CfmsException("Failed to create task: ${e.message}")
            }
        }

    override suspend fun findTaskById(taskId: String): Tasks? = trace("findTaskById") {
        queries.findByTaskId(taskId)
            ?.let { taskMapper.toDomain(it) }
    }

    override suspend fun update(task: Tasks): Unit = trace("updateTask") {
        logger.info("Updating task in repository for ID: ${task.taskId}")
        val taskRecord = taskMapper.toRecord(task)
        queries.updateTask(taskRecord)
    }

    override suspend fun deleteTaskById(taskId: String) =
        try {
            logger.info("Deleting task with ID: $taskId")
            queries.deleteTaskById(taskId)
        } catch (e: Exception) {
            logger.error("Error deleting task: ${e.message}", e)
            throw CfmsException("Failed to delete task with ID: $taskId")
        }
}
