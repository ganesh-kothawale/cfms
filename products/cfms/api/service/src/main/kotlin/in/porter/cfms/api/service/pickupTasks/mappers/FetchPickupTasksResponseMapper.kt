package `in`.porter.cfms.api.service.pickupTasks.mappers

import `in`.porter.cfms.api.models.pickupTasks.PickupTaskHlpResponse
import `in`.porter.cfms.api.models.pickupTasks.PickupTaskOrderResponse
import `in`.porter.cfms.api.models.pickupTasks.PickupTasksResponse
import `in`.porter.cfms.domain.pickuptasks.entities.PickupOrder
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask as HlpWithOrdersRecord
import javax.inject.Inject

class FetchPickupTasksResponseMapper @Inject constructor() {

    fun toResponse(
        pickups: List<HlpWithOrdersRecord>,
        page: Int,
        size: Int,
        totalPages: Int,
        totalRecords: Int
    ): PickupTasksResponse {
        return PickupTasksResponse(
            pickupTasks = pickups.map { toPickupTaskHlpResponse(it) },
            page = page,
            size = size,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }

    private fun toPickupTaskHlpResponse(hlpWithOrders: HlpWithOrdersRecord): PickupTaskHlpResponse {
        return PickupTaskHlpResponse(
            hlpOrderId = hlpWithOrders.hlpId,
            riderName = hlpWithOrders.riderName,
            riderNumber = hlpWithOrders.riderNumber,
            vehicleType = hlpWithOrders.vehicleType,
            pickupOrders = hlpWithOrders.pickupOrders.map { toPickupTaskOrderResponse(it) } // Map each PickupOrderRecord to PickupTaskOrderResponse
        )
    }

    private fun toPickupTaskOrderResponse(pickupOrder: PickupOrder): PickupTaskOrderResponse {
        return PickupTaskOrderResponse(
            orderId = pickupOrder.orderId,
            awbNmber = pickupOrder.awbNumber,
            senderName = pickupOrder.senderName,
            receiverName = pickupOrder.receiverName,
            status = pickupOrder.status,
            crNumber = pickupOrder.crNumber
        )
    }
}
