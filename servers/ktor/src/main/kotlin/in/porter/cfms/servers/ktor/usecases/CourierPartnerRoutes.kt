package `in`.porter.cfms.servers.ktor.usecases

import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.courierPartnerRoutes(httpComponent: HttpComponent) {
  post("create_record") { httpComponent.createCourierPartnerHttpService.invoke(call) }
  get("fetch_cp_records") {httpComponent.fetchCourierPartnerHttpService.invoke(call)}
}