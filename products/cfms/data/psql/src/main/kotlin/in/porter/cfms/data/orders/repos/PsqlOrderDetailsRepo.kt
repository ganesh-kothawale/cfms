package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.data.orders.mappers.OrderDetailsMapper
import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
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
        return  queries.fetchOrderDetailsByOrderNumber(orderId)
            .let { it?.let { mapper.toDomain(it) } }

    }
}