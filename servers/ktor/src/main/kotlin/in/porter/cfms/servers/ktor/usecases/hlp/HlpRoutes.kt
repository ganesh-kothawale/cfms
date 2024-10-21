package `in`.porter.cfms.servers.ktor.usecases.hlp

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.hlpRoutes(httpComponent: HttpComponent) {
    post("") { httpComponent.recordHlpDetailsHttpService.invoke(call) }
    put("") { httpComponent.updateHlpDetailsHttpService.invoke(call) }
    get("") { httpComponent.fetchHlpRecordsHttpService.invoke(call) }
}