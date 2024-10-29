package `in`.porter.cfms.data.tasks.mappers

import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.recon.ReconTable
import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.cfms.data.tasks.records.ListTaskRecord
import `in`.porter.cfms.domain.pickuptasks.TasksStatus
import `in`.porter.cfms.domain.pickuptasks.entities.PickupOrder
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.cfms.domain.recon.entities.Recon
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTaskRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(ListTaskRowMapper::class.java)

    fun toRecord(row: ResultRow): ListTaskRecord {
        logger.info("Mapping result row to ListTaskRecord")

        return ListTaskRecord(
            taskId = row[TasksTable.taskId],
            flowType = row[TasksTable.flowType],
            status = row[TasksTable.status],
            createdAt = row[TasksTable.createdAt],
            updatedAt = row[TasksTable.updatedAt],

            // Directly construct PickupTask or Recon if applicable
            pickupTaskEntity = if (row[TasksTable.flowType] == "Pickup") PickupTask(
                taskId = row[PickupTasksTable.taskId],
                hlpId = row[PickupTasksTable.hlpId],
                riderName = row[HlpsTable.riderName],
                riderNumber = row[HlpsTable.riderNumber],
                vehicleType = row[HlpsTable.vehicleType],
                pickupOrders = listOf(toPickupOrderRecord(row))
            ) else null,

            reconEntity = if (row[TasksTable.flowType] != "Pickup") Recon(
                reconId = row[ReconTable.reconId],
                orderId = row[ReconTable.orderId],
                taskId = row[TasksTable.taskId],
                teamId = row[ReconTable.teamId],
                reconStatus = row[ReconTable.reconStatus],
                packagingRequired = row[ReconTable.packagingRequired],
                prePackagingImageUrl = row[ReconTable.prePackagingImageUrl],
                shipmentIsEnvelopeOrDocument = row[ReconTable.shipmentIsEnvelopeOrDocument],
                shipmentWeight = row[ReconTable.shipmentWeight],
                weightPhotoUrl = row[ReconTable.weightPhotoUrl],
                shipmentDimensionsCmOrInch = row[ReconTable.shipmentDimensionsCmOrInch],
                shipmentLength = row[ReconTable.shipmentLength],
                shipmentWidth = row[ReconTable.shipmentWidth],
                shipmentHeight = row[ReconTable.shipmentHeight],
                dimensionsPhotoUrls = row[ReconTable.dimensionsPhotoUrls],
                returnRequested = row[ReconTable.returnRequested],
                returnImageUrl = row[ReconTable.returnImageUrl],
                createdAt = row[ReconTable.createdAt],
                updatedAt = row[ReconTable.updatedAt]
            ) else null
        )
    }

    private fun toPickupOrderRecord(resultRow: ResultRow): PickupOrder {
        logger.info("Mapping result row to PickupOrderRecord")

        val orderId = resultRow[OrdersTable.orderNumber]
        val awbNumber = resultRow[OrdersTable.awbNumber]
        val crNumber = resultRow[OrdersTable.accountCode]
        val senderName = resultRow[OrdersTable.senderName]
        val receiverName = resultRow[OrdersTable.receiverName]

        return PickupOrder(
            orderId = orderId,
            awbNumber = awbNumber.toString(),
            crNumber = crNumber,
            senderName = senderName,
            receiverName = receiverName
        )
    }
}
