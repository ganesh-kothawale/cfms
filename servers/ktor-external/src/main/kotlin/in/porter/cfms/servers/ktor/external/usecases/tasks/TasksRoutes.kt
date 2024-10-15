package `in`.porter.cfms.servers.ktor.external.usecases.tasks

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.tasksRoutes(httpComponent: HttpComponent) {

    get("") {
        try {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10

            httpComponent.listTasksHttpService.invoke(call, page, limit)
        } catch (e: CfmsException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message))))
        }
    }
}
