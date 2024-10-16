package `in`.porter.cfms.data.tasks

import `in`.porter.cfms.data.tasks.mappers.ListTasksRowMapper
import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
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
    suspend fun findAll(size: Int, offset: Int): List<TaskRecord> = transact {
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
}
