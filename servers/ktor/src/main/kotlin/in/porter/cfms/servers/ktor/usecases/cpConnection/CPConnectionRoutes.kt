package `in`.porter.cfms.servers.ktor.usecases.cpConnection

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.cpConnectionRoutes(httpComponent: HttpComponent) {
  post("") { httpComponent.recordCPConnectionHttpService.invoke(call) }
  get("") { httpComponent.fetchCPConnectionsHttpService.invoke(call) }
}
