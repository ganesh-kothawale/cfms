package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.ordersRoutes(httpComponent: HttpComponent) {
    post("/create_order") { httpComponent.createOrderHTTPService.invoke(call) }
    get ("/fetch_all_orders") { httpComponent.fetchOrdersHTTPService.invoke(call) }
    get ("/update_order_status") { httpComponent.fetchOrdersHTTPService.invoke(call) }

}