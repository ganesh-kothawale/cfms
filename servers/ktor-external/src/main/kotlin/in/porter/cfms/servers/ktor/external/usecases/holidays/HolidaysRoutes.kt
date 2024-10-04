package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.holidaysRoutes(httpComponent: HttpComponent) {

        post("/create") {
        httpComponent.createHolidaysHttpService.invoke(call)
    }

    put("") {
        httpComponent.updateHolidaysHttpService.invoke(call)

    }

    delete ("/{holidayId}") {

        runBlocking {
            val holidayId = call.parameters["holidayId"]?.toIntOrNull() ?: throw CfmsException("Invalid holiday ID")
            httpComponent.deleteHolidaysHttpService.invoke(call, holidayId)
        }
    }


}
