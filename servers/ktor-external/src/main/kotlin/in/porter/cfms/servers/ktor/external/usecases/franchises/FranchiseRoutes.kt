package `in`.porter.cfms.servers.ktor.external.usecases.franchises

import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.franchiseRoutes(httpComponent: HttpComponent) {
  post("") { httpComponent.createFranchiseRecordHttpService.invoke(call) }
}
