package `in`.porter.cfms.servers.ktor.usecases.hlp

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Route.publicHlpRoutes(httpComponent: HttpComponent) {
    get("") { httpComponent.fetchHlpRecordsHttpService.invoke(call) }
}
fun Route.privateHlpRoutes(httpComponent: HttpComponent) {
    post("") { httpComponent.recordHlpDetailsHttpService.invoke(call) }
    put("") { httpComponent.updateHlpDetailsHttpService.invoke(call) }
}
