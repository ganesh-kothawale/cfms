package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Route.publicOrdersRoutes(httpComponent: HttpComponent) {
    get("") { httpComponent.fetchOrdersHTTPService.invoke(call) }
}

fun Route.privateOrdersRoutes(httpComponent: HttpComponent) {
    post("") { httpComponent.createOrderHTTPService.invoke(call) }
    patch("") { httpComponent.updateOrderStatusHTTPService.invoke(call) }
}