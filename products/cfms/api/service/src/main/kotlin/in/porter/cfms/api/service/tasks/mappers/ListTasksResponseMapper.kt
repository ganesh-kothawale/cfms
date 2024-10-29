package `in`.porter.cfms.api.service.tasks.mappers

import `in`.porter.cfms.api.models.pickupTasks.PickupTaskHlpResponse
import `in`.porter.cfms.api.models.pickupTasks.PickupTaskOrderResponse
import `in`.porter.cfms.api.models.recon.ReconResponse
import `in`.porter.cfms.api.models.tasks.ListTasksResponse
import `in`.porter.cfms.api.models.tasks.TaskResponse
import `in`.porter.cfms.domain.pickuptasks.entities.PickupOrder
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import javax.inject.Inject

class ListTasksResponseMapper @Inject constructor() {

    fun toResponse(
        tasks: List<ListTasks>,  // List of ListTasks (domain entity)
        page: Int,
        size: Int,
        totalPages: Int,
        totalRecords: Int
    ): ListTasksResponse {
        return ListTasksResponse(
            tasks = tasks.map { toTaskResponse(it) },  // Map ListTasks to TaskResponse
            page = page,
            size = size,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }

    // Function to map ListTasks (domain entity) to TaskResponse (API response model)
    private fun toTaskResponse(task: ListTasks): TaskResponse {
        return TaskResponse(
            taskId = task.taskId,
            flowType = task.flowType,
            status = task.status,
            createdAt = task.createdAt.toString(),  // Convert LocalDateTime to String
            updatedAt = task.updatedAt.toString(),
            pickupTask = if (task.flowType == "Pickup") toPickupTaskHlpResponse(task.pickupTaskEntity) else null,
            recon = if (task.flowType != "Pickup") toReconResponse(task.reconEntity) else null  // Set recon as null if flowType is "Pickup"
        )
    }

    fun toPickupTaskHlpResponse(task: PickupTask?): PickupTaskHlpResponse? {
        return task?.let {
            PickupTaskHlpResponse(
                hlpOrderId = it.hlpId.toString(),
                riderName = it.riderName,
                riderNumber = it.riderNumber,
                vehicleType = it.vehicleType,
                pickupOrders = it.pickupOrders.map { order -> toPickupTaskOrderResponse(order) }
            )
        }
    }

    private fun toPickupTaskOrderResponse(pickupOrder: PickupOrder?): PickupTaskOrderResponse {
        return PickupTaskOrderResponse(
            orderId = pickupOrder?.orderId.toString(),
            awbNmber = pickupOrder?.awbNumber,
            senderName = pickupOrder?.senderName,
            receiverName = pickupOrder?.receiverName,
            crNumber = pickupOrder?.crNumber
        )
    }

    private fun toReconResponse(recon: Recon?): ReconResponse {
        return ReconResponse(
            reconId = recon?.reconId.toString(),
            orderId = recon?.orderId.toString(),
            taskId = recon?.taskId.toString(),
            teamId = recon?.teamId.toString(),
            reconStatus = recon?.reconStatus,
            packagingRequired = recon?.packagingRequired,
            prePackagingImageUrl = recon?.prePackagingImageUrl,
            shipmentIsEnvelopeOrDocument = recon?.shipmentIsEnvelopeOrDocument,
            shipmentWeight = recon?.shipmentWeight,
            weightPhotoUrl = recon?.weightPhotoUrl,
            shipmentDimensionsCmOrInch = recon?.shipmentDimensionsCmOrInch,
            shipmentLength = recon?.shipmentLength,
            shipmentWidth = recon?.shipmentWidth,
            shipmentHeight = recon?.shipmentHeight,
            dimensionsPhotoUrls = recon?.dimensionsPhotoUrls,
            returnRequested = recon?.returnRequested,
            returnImageUrl = recon?.returnImageUrl,
            createdAt = recon?.createdAt,
            updatedAt = recon?.updatedAt
        )
    }
}


