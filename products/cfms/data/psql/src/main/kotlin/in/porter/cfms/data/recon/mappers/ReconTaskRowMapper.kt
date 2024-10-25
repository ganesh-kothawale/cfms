package `in`.porter.cfms.data.recon.mappers

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.recon.ReconTable
import `in`.porter.cfms.data.recon.records.ReconTaskRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ReconTaskRowMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(ReconTaskRowMapper::class.java)

    fun toRecord(resultRow: ResultRow): ReconTaskRecord {
        logger.info("Mapping result row to ReconTaskRecord")
        return ReconTaskRecord(
            taskId = resultRow[ReconTable.taskId],
            crId = resultRow[OrdersTable.orderNumber],
            cpName = resultRow[OrdersTable.courierPartner],
            cpImageUrl = null,
            shipmentImageUrl = null,
            awb = resultRow[OrdersTable.awbNumber]?.toString() ?: "UNKNOWN",
            status = resultRow[ReconTable.reconStatus]?.toString() ?: "UNKNOWN"
        )
    }
}