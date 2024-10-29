package `in`.porter.cfms.servers.ktor.usecases.packagingissues

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueRequest
import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.time.LocalDate
import kotlin.text.toIntOrNull

fun Route.packageIssuesRoutes(httpComponent: HttpComponent) {

    get("") {
        try {
            val request = ListPackageIssueRequest(
                page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1,
                size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10,
                createdDate = call.request.queryParameters["created_date"]?.let { LocalDate.parse(it) },
                updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDate.parse(it) },
                orderId = call.request.queryParameters["order_id"]?.split(",")?.map { it.trim() },
                awbNo = call.request.queryParameters["awb_no"]?.split(",")?.map { it.trim() },
                franchiseId = call.request.queryParameters["franchise_id"]?.split(",")?.map { it.trim() },
                action = call.request.queryParameters["action"]
            )
            httpComponent.packageIssuesHttpService.invoke(call, request)

        } catch (e: CfmsException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message)))
            )
        }
    }
}