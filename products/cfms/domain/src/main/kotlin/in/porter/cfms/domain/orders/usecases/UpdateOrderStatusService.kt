package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.UpdateOrderStatusRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class UpdateOrderStatusService
@Inject constructor(
    val repo: OrderDetailsRepo,
    val statusMapper: OrderStatusMapper
) {

    suspend fun invoke(request: UpdateOrderStatusRequest): Int =
        statusMapper.invoke(request.status)
            ?.let { repo.updateStatus(request.orderId, it) }
            ?: throw IllegalArgumentException("Invalid order status: ${request.status}")
}