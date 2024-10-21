package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.pickuptasks.records.PickupTaskRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PickupTasksRowMapper @Inject constructor()  {
    private val logger = LoggerFactory.getLogger(PickupTasksRowMapper::class.java)
    fun toRecord(resultRow: ResultRow): PickupTaskRecord {
        logger.info("Mapping result row to PickupTaskRecord")
        return PickupTaskRecord(
            taskId = resultRow[PickupTasksTable.taskId].toString(),
            orderId = resultRow[PickupTasksTable.orderId],
            hlpId = resultRow[PickupTasksTable.hlpId],
            franchiseId = resultRow[PickupTasksTable.franchiseId],
            status = resultRow[PickupTasksTable.status],
            createdAt = resultRow[PickupTasksTable.createdAt],
            updatedAt = resultRow[PickupTasksTable.updatedAt]
        )
    }
}