package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.pickuptasks.records.PickupDetailsRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PickupDetailsRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(PickupDetailsRowMapper::class.java)

    fun toRecord(row: ResultRow): PickupDetailsRecord {
        logger.info("Mapping result row to PickupDetailsRecord")
        return PickupDetailsRecord(
            pickupDetailsId = row[PickupTasksTable.pickupTaskId],
            taskId = row[PickupTasksTable.taskId],
            orderId = row[PickupTasksTable.orderId],
            hlpId = row[PickupTasksTable.hlpId],
            franchiseId = row[PickupTasksTable.franchiseId],
            status = row[PickupTasksTable.status],
            createdAt = row[PickupTasksTable.createdAt],
            updatedAt = row[PickupTasksTable.updatedAt]
        )
    }
}
