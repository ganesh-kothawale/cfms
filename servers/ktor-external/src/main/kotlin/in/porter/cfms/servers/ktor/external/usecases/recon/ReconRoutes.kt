package `in`.porter.cfms.servers.ktor.external.usecases.recon

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

fun Route.reconRoutes(httpComponent: HttpComponent) {
    val logger = LoggerFactory.getLogger("TasksRoutes")

    get("") {
        try {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10
            httpComponent.listReconHttpService.invoke(call, page, size)

        } catch (e: CfmsException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message)))
            )
        }
    }
}
