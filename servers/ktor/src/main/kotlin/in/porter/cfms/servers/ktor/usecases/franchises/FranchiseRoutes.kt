package `in`.porter.cfms.servers.ktor.usecases.franchises

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.franchises.ListFranchisesRequest
import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import java.time.LocalDate

fun Route.franchiseRoutes(httpComponent: HttpComponent) {
    post("") { httpComponent.createFranchiseRecordHttpService.invoke(call) }

    get("") {
        try {
            val request = ListFranchisesRequest(
                page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1,
                size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10,
                createdDate = call.request.queryParameters["created_date"]?.let { LocalDate.parse(it) },
                updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDate.parse(it) },
                franchiseId = call.request.queryParameters["franchise_id"],
                pocPrimaryNumber = call.request.queryParameters["poc_primary_number"],
                emailId = call.request.queryParameters["email_id"],
                porterHubName = call.request.queryParameters["porter_hub_name"],
                status = call.request.queryParameters["status"],
                kam = call.request.queryParameters["kam"],
                city = call.request.queryParameters["city"],
                state = call.request.queryParameters["state"],
                pincode = call.request.queryParameters["pincode"]?.toIntOrNull(),
                geoRegionId = call.request.queryParameters["geo_region_id"],
                radiusCoverage = call.request.queryParameters["radius_coverage"]?.toIntOrNull()
            )

            httpComponent.listFranchisesHttpService.invoke(call, request)
        } catch (e: CfmsException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid query parameters",
                            "details" to e.message
                        )
                    )
                )
            )
        }
    }
    put("") { httpComponent.updateFranchiseRecordHttpService.invoke(call) }
}
