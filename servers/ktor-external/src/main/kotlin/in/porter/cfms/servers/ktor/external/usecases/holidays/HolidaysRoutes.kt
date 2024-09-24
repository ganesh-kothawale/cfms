package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.holidaysRoutes(httpComponent: HttpComponent) {


    // Create Holiday
    post("/create") {
        httpComponent.createHolidaysHttpService.invoke(call)
    }


}