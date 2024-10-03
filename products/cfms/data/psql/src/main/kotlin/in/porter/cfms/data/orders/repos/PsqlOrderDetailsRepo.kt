package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.data.orders.mappers.OrderDetailsMapper
import `in`.porter.cfms.data.orders.mappers.OrderStatusMapper
import `in`.porter.cfms.domain.orders.entities.*
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class PsqlOrderDetailsRepo
@Inject constructor(
    val queries: OrderDetailsQueries,
    val mapper: OrderDetailsMapper,
    val statusMapper: OrderStatusMapper
) : OrderDetailsRepo {

    override suspend fun createOrder(request: CreateOrderRequest):Int {
       return  queries.createOrder(request)
    }

    override suspend fun fetchOrderByCourierId(orderId: String): Order? {
        return  queries.fetchOrderDetailsByOrderNumber(orderId)
            .let { it?.let { mapper.toDomain(it) } }

    }

    override suspend fun fetchOrders(request: FetchOrdersRequest): List<Order> {
       return queries.fetchOrders(request.limit, request.limit * (request.page - 1),request.franchiseId)
            .let  {it.map { mapper.toDomain(it) }}
    }

    override suspend fun getOrderCount(request: FetchOrdersRequest): Int {
       return  queries.getOrderCount()
    }
    override  suspend fun  updateStatus(orderId: Int, status: OrderStatus): Int =
    statusMapper.toString(status)
        ?.let { queries.updateStatus(orderId, it) }
    ?: throw IllegalArgumentException("Invalid order status: $status")
}