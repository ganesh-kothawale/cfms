package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.UpdateOrderStatusRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class UpdateOrderStatusService
@Inject constructor(

    val repo: OrderDetailsRepo

) {

    suspend fun invoke(request: UpdateOrderStatusRequest): Int = 1
    repo.updateOrderStatus(request)
}