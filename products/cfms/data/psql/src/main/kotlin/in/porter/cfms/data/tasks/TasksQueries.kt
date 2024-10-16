package `in`.porter.cfms.data.tasks

import `in`.porter.cfms.data.tasks.mappers.ListTasksRowMapper
import `in`.porter.cfms.data.tasks.records.ListTasksRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory
import java.time.Instant
import javax.inject.Inject

class TasksQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val listTasksMapper: ListTasksRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(TasksQueries::class.java)

    // Retrieve tasks with pagination
    suspend fun findAll(size: Int, offset: Int): List<ListTasksRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching tasks with size: $size and offset: $offset")
        TasksTable
            .selectAll()
            .limit(size, offset)
            .map { row ->
                logger.info("Mapping row: $row")
                listTasksMapper.toRecord(row)
            }
    }

    // Count total number of tasks in the database
    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all tasks")
        TasksTable
            .selectAll()
            .count()
            .toInt()
    }

    // Retrieve tasks by their IDs
    suspend fun findByIds(taskIds: List<Int>): List<ListTasksRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching tasks with IDs: $taskIds")
        TasksTable
            .select { TasksTable.taskId inList taskIds }
            .map { row ->
                logger.info("Mapping row: $row")
                listTasksMapper.toRecord(row)
            }
    }

    // Update the status of tasks based on task IDs
    suspend fun updateStatus(taskIds: List<Int>, status: String) = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Updating status for tasks with IDs: $taskIds to status: $status")
        TasksTable
            .update({ TasksTable.taskId inList taskIds }) {
                it[TasksTable.status] = status
                it[TasksTable.updatedAt] = Instant.now()
            }
        logger.info("Successfully updated status for tasks: $taskIds")
    }
}
