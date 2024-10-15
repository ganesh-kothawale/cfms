package `in`.porter.cfms.servers.ktor.external.usecases.franchises

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import java.time.LocalDate

fun Route.franchiseRoutes(httpComponent: HttpComponent) {
    post("") { httpComponent.createFranchiseRecordHttpService.invoke(call) }

    get("") {
        try {
            // Extract query parameters
            val page =
                call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10

            httpComponent.listFranchisesHttpService.listAllHolidays(call, page, size)
        } catch (e: CfmsException) {
            // Handle any validation or service-related exceptions
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid page or size parameter",
                            "details" to "Page must be a positive integer, and size must be between 1 and 100."
                        )
                    )
                )
            )
        }
    }
}
