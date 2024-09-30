package `in`.porter.cfms.domain.orders.repos

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest
import `in`.porter.cfms.domain.orders.entities.Order

interface OrderDetailsRepo {
    suspend fun createOrder(request: CreateOrderRequest):Int
    suspend fun fetchOrderByCourierId(orderId: String): Order?
    suspend fun fetchOrders(request: FetchOrdersRequest):  List<Order>
    suspend fun getOrderCount(request: FetchOrdersRequest):  Int

}