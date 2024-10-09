package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.usecases.UpdateHolidaysService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import java.time.format.DateTimeParseException
import javax.inject.Inject

class UpdateHolidaysHttpService
@Inject
constructor(
    private val service: UpdateHolidaysService
) : Traceable {
    private val logger = LoggerFactory.getLogger(UpdateHolidaysHttpService::class.java)

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                logger.info("Received request to update holiday")

                val request = try {
                    call.receive<UpdateHolidaysRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to CreateHolidaysRequest: ${e.message}")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid request format.",
                            "details" to e.message
                        )
                    )
                    return@trace
                }

                // Call the service to update the holiday
                service.invoke(request)

                logger.info("Holiday updated successfully")
                call.respond(HttpStatusCode.OK, mapOf("message" to "Holiday updated successfully"))

            } catch (e: CfmsException) {
                logger.error("Error occurred while updating holiday: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: DateTimeParseException) {
                logger.error("Invalid date format: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid date format", "details" to e.message))
            } catch (e: IllegalArgumentException) {
                logger.error("Validation error: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Validation error", "details" to e.message))
            } catch (e: Exception) {
                logger.error("Internal server error: ${e.message}")
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Failed to update holiday"))
            }
        }
    }
}
