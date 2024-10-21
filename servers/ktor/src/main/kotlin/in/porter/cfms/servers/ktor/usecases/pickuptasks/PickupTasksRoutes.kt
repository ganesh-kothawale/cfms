package `in`.porter.cfms.servers.ktor.usecases.pickuptasks

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.pickupTasksRoutes(httpComponent: HttpComponent) {
    get("") { httpComponent.fetchPickupTasksHttpService.invoke(call) }
}