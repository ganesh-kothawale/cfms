package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Route.holidaysRoutes(httpComponent: HttpComponent) {

        post("/create") {
        httpComponent.createHolidaysHttpService.invoke(call)
    }

    patch("/{holidayId}") {
        runBlocking {
            val holidayId = call.parameters["holidayId"]?.toLongOrNull() ?: throw CfmsException("Invalid holiday ID")
            httpComponent.updateHolidaysHttpService.invoke(call, holidayId)
        }
    }


}