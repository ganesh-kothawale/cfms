package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.LeaveType
import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
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
            val holidayId = call.parameters["holidayId"]?.toIntOrNull() ?: throw CfmsException("Invalid holiday ID")
            httpComponent.deleteHolidaysHttpService.invoke(call, holidayId)
        }
    }

    get("") {
        try {
            // Extract query parameters
            val page =
                call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10
            val franchiseId = call.request.queryParameters["franchise_id"]?.takeIf { it.isNotBlank() }
            val leaveType = call.request.queryParameters["leave_type"]?.takeIf { it.isNotBlank() }
            val startDate = call.request.queryParameters["start_date"]?.let { LocalDate.parse(it) }
            val endDate = call.request.queryParameters["end_date"]?.let { LocalDate.parse(it) }

            // Invoke the list holidays HTTP service
            httpComponent.listHolidaysHttpService.invoke(call, page, size, franchiseId, leaveType, startDate, endDate)
        } catch (e: CfmsException) {
            // Handle any validation or service-related exceptions
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message))))
        }
    }
}
