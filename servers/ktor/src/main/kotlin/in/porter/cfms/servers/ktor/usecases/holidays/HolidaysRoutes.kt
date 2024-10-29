package `in`.porter.cfms.servers.ktor.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

fun Route.holidaysRoutes(httpComponent: HttpComponent) {

    post("") {
        httpComponent.createHolidaysHttpService.invoke(call)
    }

    put("") {
        httpComponent.updateHolidaysHttpService.invoke(call)

    }

    delete("/{holidayId}") {

        runBlocking {
            val holidayId = call.parameters["holidayId"]
            httpComponent.deleteHolidaysHttpService.invoke(call, holidayId.toString())
        }
    }

    get("") {
        try {
            val request = ListHolidaysRequest(
            page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1,
            size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10,
            franchiseIds = call.request.queryParameters["franchise_id"]?.split(",")?.map { it.trim() },
            backupFranchises = call.request.queryParameters["backup_franchise"]?.split(",")?.map { it.trim() },
            leaveType = call.request.queryParameters["leave_type"],
            createdDate = call.request.queryParameters["created_date"]?.let { LocalDate.parse(it) },
            updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDate.parse(it) },
            fromDate = call.request.queryParameters["from_date"]?.let { LocalDate.parse(it) },
            toDate = call.request.queryParameters["to_date"]?.let { LocalDate.parse(it) }
            )


            httpComponent.listHolidaysHttpService.invoke(call, request)
        } catch (e: CfmsException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message))))
        }
    }
}
