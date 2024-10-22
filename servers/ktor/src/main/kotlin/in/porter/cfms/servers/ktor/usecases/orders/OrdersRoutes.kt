package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.ordersRoutes(httpComponent: HttpComponent) {

    get ("/fetch_all_orders") { httpComponent.fetchOrdersHTTPService.invoke(call) }
    patch  ("/update_order_status") { httpComponent.updateOrderStatusHTTPService.invoke(call) }

}