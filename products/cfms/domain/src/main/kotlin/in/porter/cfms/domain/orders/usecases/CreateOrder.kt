package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class CreateOrder
@Inject constructor(
    private val repo: OrderDetailsRepo,
) {
    suspend fun invoke(createOrderRequest: CreateOrderRequest): Int {
        return try {
            repo.createOrder(createOrderRequest)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }
}
