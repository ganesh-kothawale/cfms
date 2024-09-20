package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class ValidateOrderDetails
@Inject constructor(
    private val orderDetailsRepo: OrderDetailsRepo,
//    private val franchiseRepo: FranchiseRepo
) {

    suspend fun validateOrderRequest(createOrderRequest: CreateOrderRequest) {
        if (orderExists(createOrderRequest.basicDetails.orderNumber)) {
            throw IllegalArgumentException("Order ID already exists")
        }
//        if (!franchiseAndTeamExist(createOrderRequest.basicDetails.associationDetails.franchiseId)) {
//            throw IllegalArgumentException("Franchise ID does not exist")
//        }
    }

    private suspend fun orderExists(orderId: String): Boolean {
        val order = orderDetailsRepo.fetchOrderByCourierId(orderId)
        return order != null
    }

//    private suspend fun franchiseAndTeamExist(franchiseId: String): Boolean {
//        val franchise = franchiseRepo.fetchFranchiseById(franchiseId)
//        return franchise != null
//    }
}