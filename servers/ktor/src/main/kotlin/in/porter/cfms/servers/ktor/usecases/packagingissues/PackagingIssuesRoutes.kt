package `in`.porter.cfms.servers.ktor.usecases.packagingissues

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlin.text.toIntOrNull

fun Route.packagingIssuesRoutes(httpComponent: HttpComponent) {

    get("") {
        try {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10
            httpComponent.packagingIssuesHttpService.invoke(call, page, size)

        } catch (e: CfmsException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message)))
            )
        }
    }
}