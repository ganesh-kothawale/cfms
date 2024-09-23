package `in`.porter.cfms.domain.orders.repos

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.entities.Order

interface OrderDetailsRepo {
    suspend fun createOrder(order: CreateOrderRequest)
    suspend fun fetchOrderByCourierId(orderId: String): Order?

}