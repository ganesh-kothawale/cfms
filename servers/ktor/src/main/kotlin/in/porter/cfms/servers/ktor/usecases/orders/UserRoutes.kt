package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.userROutes(httpComponent: HttpComponent) {
    post("/create_order") { httpComponent..invoke(call) }