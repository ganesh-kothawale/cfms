package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse
import `in`.porter.cfms.domain.common.entities.Pagination
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class FetchOrdersService @Inject constructor(
   private  val repo: OrderDetailsRepo
) {

    suspend fun invoke(request: FetchOrdersRequest): FetchOrdersResponse {
        val validatedRequest = FetchOrdersRequest(
            page = request.page?.coerceAtLeast(1) ?: 1,
            limit = request.limit?.coerceAtLeast(1) ?: 10
        )
       val orders = repo.fetchOrders(validatedRequest)
        val totalCount = repo.getOrderCount(validatedRequest)
        val pagination = Pagination(
            page = request.page+1,
            limit = request.limit,
            totalPages = (totalCount + request.limit - 1) / request.limit
        )
        return FetchOrdersResponse(
            orders = orders,
            pagination = pagination
        )
    }


}