package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse
import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.common.entities.paginationToken
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class FetchOrdersService @Inject constructor(
   private  val orderDetailsRepo: OrderDetailsRepo
) {

    suspend fun invoke(request: FetchOrdersRequest): FetchOrdersResponse {
        orderDetailsRepo.fetchOrders(request)

    }


}