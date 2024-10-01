package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.UpdateOrderStatusRequest
import javax.inject.Inject

class UpdateOrderStatusService
@Inject constructor() {

    suspend fun invoke(request: UpdateOrderStatusRequest): Int {
        // Dummy implementation
        return 1
    }
}