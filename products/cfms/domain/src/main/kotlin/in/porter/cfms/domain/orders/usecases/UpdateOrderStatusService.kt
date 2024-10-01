package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.UpdateOrderStatusRequest

class UpdateOrderStatusService {

    suspend fun invoke(request: UpdateOrderStatusRequest): Int {
        // Dummy implementation
        return 1
    }
}