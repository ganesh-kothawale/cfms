package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse
import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class PsqlOrderDetailsRepo
@Inject constructor(
    val queries: OrderDetailsQueries,
    val mapper: OrderDetailsMapper
) : OrderDetailsRepo {

    override suspend fun createOrder(request: CreateOrderRequest) {
        queries.createOrder(request)
    }

    override suspend fun fetchOrderByCourierId(orderId: String): Order? {
        val query = queries.fetchOrderDetailsByOrderNumber(orderId)
        return query?.mapNotNull { row: ResultRow -> mapper.toDomain(row) }?.singleOrNull()
    }

    override suspend fun fetchOrders(request: FetchOrdersRequest): List<Order> {
       val orders= queries.fetchOrders(request.limit, request.limit * (request.page - 1))
            .let  {mapper.mapOrders(it)}
        return  orders
    }

    override suspend fun getOrderCount(request: FetchOrdersRequest): Int {
       return  queries.getOrderCount()
    }
}