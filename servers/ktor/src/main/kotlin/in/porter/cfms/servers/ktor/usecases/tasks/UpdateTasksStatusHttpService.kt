package `in`.porter.cfms.servers.ktor.usecases.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.tasks.UpdateTaskStatusRequest
import `in`.porter.cfms.api.service.tasks.usecases.UpdateTasksStatusService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateTasksStatusHttpService @Inject constructor(
    private val updateTasksStatusService: UpdateTasksStatusService
) : Traceable {

    private val logger = LoggerFactory.getLogger(UpdateTasksStatusHttpService::class.java)

    suspend fun invoke(call: ApplicationCall) {
        try {
            logger.info("Entered UpdateTasksStatusHttpService.invoke method")

            // Parse the request body manually with Jackson
            val rawBody = call.receiveText()
            logger.info("Raw request body: $rawBody")

            val objectMapper = jacksonObjectMapper()
            val request = try {
                objectMapper.readValue(rawBody, UpdateTaskStatusRequest::class.java)
            } catch (e: Exception) {
                logger.error("Failed to convert request body to UpdateTaskStatusRequest: ${e.message}")
                call.respond(
                    HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid request format.",
                        "details" to e.message
                    )
                )
                return
            }
                logger.info("Received request to update task statuses: $request")

                // Call the service to update task statuses
               val updatedTasks = updateTasksStatusService.updateTasksStatus(request)

                // Return success response if all tasks were updated
                call.respond(
                    HttpStatusCode.OK,
                   mapOf("message" to "Task status updated successfully", "updated_tasks" to updatedTasks)
                )
            } catch (e: CfmsException) {
                // Handle validation errors or specific business logic errors
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to mapOf("message" to "Failed to update tasks", "details" to e.message))
                )
            } catch (e: NoSuchElementException) {
                // Handle case where one or more task IDs are not found
                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf(
                        "error" to mapOf(
                            "message" to "Task not found",
                            "details" to "One or more task IDs could not be found."
                        )
                    )
                )
            } catch (e: Exception) {
                // Handle unexpected errors
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf(
                        "error" to mapOf(
                            "message" to "Failed to update task statuses",
                            "details" to "An unexpected error occurred on the server."
                        )
                    )
                )
            }
        }
    }
