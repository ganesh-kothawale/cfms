package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.pickuptasks.mappers.PickupTasksRowMapper
import `in`.porter.cfms.data.pickuptasks.records.PickupTaskRecord
import `in`.porter.cfms.data.tasks.TasksQueries
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

class PickupTasksQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val pickupTasksRowMapper: PickupTasksRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(PickupTasksQueries::class.java)
    suspend fun findAll(size: Int, offset: Int): List<PickupTaskRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Retrieving pickup-tasks with size: $size, offset: $offset")
        PickupTasksTable
            .selectAll()
            .limit(size, offset)
            .map { row ->
                pickupTasksRowMapper.toRecord(row)
            }
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