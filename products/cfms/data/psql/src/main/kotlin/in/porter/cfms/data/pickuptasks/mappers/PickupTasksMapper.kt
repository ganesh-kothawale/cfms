package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.pickuptasks.records.HlpWithOrdersRecord
import `in`.porter.cfms.domain.pickuptasks.TasksStatus
import `in`.porter.cfms.domain.pickuptasks.entities.PickupOrder
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import javax.inject.Inject

class PickupTasksMapper @Inject constructor() {
    fun toDomain(record: HlpWithOrdersRecord): PickupTask {
        return PickupTask(
            taskId = record.taskId,
            status = TasksStatus.valueOf(record.pickupOrders.first().status),
            hlpId = record.hlpOrderId,
            riderName = record.riderName ?: "Unknown Rider",
            riderNumber = record.riderNumber ?: "Unknown Number",
            vehicleType = record.vehicleType ?: "Unknown Vehicle",
            pickupOrders = record.pickupOrders.map { orderRecord ->
                PickupOrder(
                    orderId = orderRecord.orderId.toString(),
                    awbNumber = orderRecord.awbNumber ?: "Unknown AWB",
                    senderName = orderRecord.senderName ?: "Unknown Sender",
                    receiverName = orderRecord.receiverName ?: "Unknown Receiver",
                    status = orderRecord.status,
                    crNumber = orderRecord.crNumber ?: "Unknown CR"
                )
            }
        )
    }
}