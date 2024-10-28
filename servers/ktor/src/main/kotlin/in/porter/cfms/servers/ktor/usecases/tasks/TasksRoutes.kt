package `in`.porter.cfms.servers.ktor.usecases.tasks

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.tasks.ListTasksRequest
import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import java.time.LocalDate

fun Route.tasksRoutes(httpComponent: HttpComponent) {
    val logger = LoggerFactory.getLogger("TasksRoutes")

    get("") {
        try {
            // Construct the ListTasksRequest from query parameters
            val request = ListTasksRequest(
                page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1,
                size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10,
                createdDate = call.request.queryParameters["created_date"]?.let { LocalDate.parse(it) },
                updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDate.parse(it) },
                taskType = call.request.queryParameters["task_type"],
                franchiseIds = call.request.queryParameters["franchise_id"]?.split(",")?.map { it.trim() },
                orderIds = call.request.queryParameters["order_id"]?.split(",")?.map { it.trim() },
                awbNumbers = call.request.queryParameters["awb_number"]?.split(",")?.map { it.trim() },
                taskStatus = call.request.queryParameters["task_status"]
            )

            // Invoke the HTTP service with the request
            httpComponent.listTasksHttpService.invoke(call, request)
        } catch (e: CfmsException) {
            // Handle any validation or parameter errors
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to listOf(mapOf("message" to "Invalid request parameters", "details" to e.message)))
            )
        }
    }

    put("/status") {
        logger.info("PUT request to /cfms/public/tasks/status received")
        httpComponent.updateTasksStatusHttpService.invoke(call)
    }
}
