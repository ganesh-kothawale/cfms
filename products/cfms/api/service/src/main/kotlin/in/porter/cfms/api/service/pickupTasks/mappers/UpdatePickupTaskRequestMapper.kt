package `in`.porter.cfms.api.service.pickupTasks.mappers

import `in`.porter.cfms.api.models.pickupTasks.UpdatePickupTaskRequest
import `in`.porter.cfms.domain.pickuptasks.entities.UpdatePickupTask
import `in`.porter.cfms.domain.pickuptasks.entities.Order
import javax.inject.Inject

class UpdatePickupTaskRequestMapper @Inject constructor() {

    // This function maps the UpdatePickupTaskRequest to the PickupTask domain entity
    fun toDomain(request: UpdatePickupTaskRequest): UpdatePickupTask {
        return UpdatePickupTask(
            taskId = request.taskId,
            noOfPackagesReceived = request.noOfPackagesReceived,
            taskStatus = request.taskStatus,
            orders = request.orders.map { toDomainOrder(it) }, // Map the list of orders
            orderImage = (request.orderImage).toString() // Handle nullable order images
        )
    }

    // Helper function to map each order in the request to the Order domain entity
    private fun toDomainOrder(orderRequest: `in`.porter.cfms.api.models.pickupTasks.Order): Order {
        return Order(
            orderId = orderRequest.orderId,
            status = orderRequest.status
        )
    }
}
