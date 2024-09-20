package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class CreateOrderService
@Inject constructor(
    private val repo: OrderDetailsRepo,
    private val validateOrderDetails: ValidateOrderDetails,
) {
   suspend fun invoke(createOrderRequest: CreateOrderRequest) {
        try {
            validateOrderDetails.validateOrderRequest(createOrderRequest)
            repo.createOrder(createOrderRequest)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }
}