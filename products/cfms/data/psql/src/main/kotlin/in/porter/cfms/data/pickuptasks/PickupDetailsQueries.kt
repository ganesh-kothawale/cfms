package `in`.porter.cfms.data.pickuptasks

import `in`.porter.cfms.data.pickuptasks.mappers.PickupDetailsRowMapper
import `in`.porter.cfms.data.pickuptasks.records.PickupDetailsRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PickupDetailsQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val pickupDetailsRowMapper: PickupDetailsRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(PickupDetailsQueries::class.java)

    // Insert a new PickupDetailsRecord into the database
    suspend fun insert(pickupDetailsRecord: PickupDetailsRecord): String = transact {
        logger.info("Inserting new pickup details into the database")

        PickupTasksTable.insert { row ->
            row[pickupTaskId] = pickupDetailsRecord.pickupDetailsId
            row[taskId] = pickupDetailsRecord.taskId
            row[orderId] = pickupDetailsRecord.orderId
            row[hlpId] = pickupDetailsRecord.hlpId
            row[franchiseId] = pickupDetailsRecord.franchiseId
            row[status] = pickupDetailsRecord.status
            row[createdAt] = pickupDetailsRecord.createdAt
            row[updatedAt] = pickupDetailsRecord.updatedAt
        }

        // Return the generated pickupDetailsId
        pickupDetailsRecord.pickupDetailsId
    }

    suspend fun findByTaskId(taskId: String): PickupDetailsRecord? = transact {
        PickupTasksTable
            .select { PickupTasksTable.taskId eq taskId }
            .map { pickupDetailsRowMapper.toRecord(it) }
            .firstOrNull()
    }

    suspend fun deleteTaskById(taskId: String): Unit = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Deleting Pickup Details with ID: $taskId")
        PickupTasksTable.deleteWhere { PickupTasksTable.taskId eq taskId }
    }
}
