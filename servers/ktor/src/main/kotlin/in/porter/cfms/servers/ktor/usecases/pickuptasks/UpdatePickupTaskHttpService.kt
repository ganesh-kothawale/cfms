package `in`.porter.cfms.servers.ktor.usecases.pickuptasks

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.pickupTasks.UpdatePickupTaskRequest
import `in`.porter.cfms.api.service.pickupTasks.usecases.UpdatePickupTaskService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdatePickupTaskHttpService @Inject constructor(
    private val updatePickupTaskService: UpdatePickupTaskService
) : Traceable {

    private val logger = LoggerFactory.getLogger(UpdatePickupTaskHttpService::class.java)

    suspend fun invoke(call: ApplicationCall) {
        try {
            val request = call.receive<UpdatePickupTaskRequest>()
            updatePickupTaskService.invoke(request)

            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "data" to mapOf(
                        "message" to "Pickup task updated successfully",
                        "task_id" to request.taskId
                    ),
                    "error" to emptyList<String>()
                )
            )
        } catch (e: CfmsException) {
            // Handle validation or business logic errors
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid request format.",
                            "details" to e.message
                        )
                    )
                )
            )
        } catch (e: NoSuchElementException) {
            // Handle case where task or order IDs are not found
            call.respond(
                HttpStatusCode.NotFound,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Not found",
                            "details" to "The task or order does not exist."
                        )
                    )
                )
            )
        } catch (e: Exception) {
            // Handle unexpected errors
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Pickup task update failed",
                            "details" to "An unexpected error occurred on the server."
                        )
                    )
                )
            )
        }
    }
}
