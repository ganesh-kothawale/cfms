package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord
import `in`.porter.cfms.data.pickuptasks.records.PickupOrderRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PickupTasksRowMapper @Inject constructor()  {
    private val logger = LoggerFactory.getLogger(PickupTasksRowMapper::class.java)
    fun toRecord(resultRow: ResultRow): HlpWithOrdersRecord {
        logger.info("Mapping result row to HlpWithOrdersRecord")

        val taskId = resultRow[PickupTasksTable.taskId] ?: "Unknown Task"
        val hlpOrderId = resultRow[PickupTasksTable.hlpId] ?: "Unknown HLP ID"
        val riderName = resultRow[HlpsTable.riderName] ?: "Unknown Rider"
        val riderNumber = resultRow[HlpsTable.riderNumber] ?: "Unknown Number"
        val vehicleType = resultRow[HlpsTable.vehicleType] ?: "Unknown Vehicle"

        // Mapping the inner `PickupOrderRecord`
        val pickupOrderRecord = toPickupOrderRecord(resultRow)

        return HlpWithOrdersRecord(
            taskId = taskId.toString(),
            hlpOrderId = hlpOrderId,
            riderName = riderName,
            riderNumber = riderNumber,
            vehicleType = vehicleType,
            pickupOrders = listOf(pickupOrderRecord) // A list with one order record
        )
    }

    private fun toPickupOrderRecord(resultRow: ResultRow): PickupOrderRecord {
        logger.info("Mapping result row to PickupOrderRecord")

        val orderId = resultRow[OrdersTable.orderNumber] ?: "Unknown Order"
        val awbNumber = resultRow[OrdersTable.awbNumber] ?: "Unknown AWB"
        val crNumber = resultRow[OrdersTable.accountCode] ?: "Unknown CR"
        val status = resultRow[PickupTasksTable.status] ?: "Unknown Status"
        val senderName = resultRow[OrdersTable.senderName] ?: "Unknown Sender"
        val receiverName = resultRow[OrdersTable.receiverName] ?: "Unknown Receiver"

        return PickupOrderRecord(
            orderId = orderId,
            awbNumber = awbNumber,
            crNumber = crNumber,
            status = status,
            senderName = senderName,
            receiverName = receiverName
        )
    }
}