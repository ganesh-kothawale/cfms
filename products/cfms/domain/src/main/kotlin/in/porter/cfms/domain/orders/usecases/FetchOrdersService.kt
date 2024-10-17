package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse
import `in`.porter.cfms.domain.common.entities.Pagination
import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import javax.inject.Inject

class FetchOrdersService @Inject constructor(
    private val repo: OrderDetailsRepo,
) {

    private fun generateResponse(
        orders: List<Order>,
        totalCount: Int,
        request: FetchOrdersRequest
    ): FetchOrdersResponse = FetchOrdersResponse(
        orders = orders,
        page = request.page + 1,
        size = request.size,
        totalPages = (totalCount + request.size - 1) / request.size,
        totalRecords = totalCount
    )

    suspend fun invoke(request: FetchOrdersRequest): FetchOrdersResponse = try {
        val totalCount = repo.getOrderCount(request)
        val orders = repo.fetchOrders(request)
        generateResponse(orders, totalCount, request)
    } catch (e: Exception) {
        println("Error executing fetch orders: ${e.message}")
        throw e
    }
}