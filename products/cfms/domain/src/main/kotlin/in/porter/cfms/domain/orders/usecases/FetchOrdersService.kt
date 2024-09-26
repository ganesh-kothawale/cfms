package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse
import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.common.entities.Pagination
import javax.inject.Inject

class FetchOrdersService @Inject constructor() {

    suspend fun invoke(request: FetchOrdersRequest): FetchOrdersResponse {
        // Placeholder for fetching data from the repository
        val orders: List<Order> = listOf() // This will be replaced with actual data fetching logic
        val pagination = Pagination(
            page = request.page,
            totalPages = 1, // This will be calculated based on the data
            limit = request.limit
        )

        return FetchOrdersResponse(
            orders = orders,
            pagination = pagination
        )
    }
}