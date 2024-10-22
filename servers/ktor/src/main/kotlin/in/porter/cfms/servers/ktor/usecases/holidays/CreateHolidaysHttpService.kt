package `in`.porter.cfms.servers.ktor.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.usecases.CreateHolidaysService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreateHolidaysHttpService
@Inject
constructor(
    private val service: CreateHolidaysService,
) : Traceable {

    private val logger = LoggerFactory.getLogger(CreateHolidaysHttpService::class.java)

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                logger.info("Received request to create a holiday")

                // Directly deserialize the request into CreateHolidaysRequest
                val request = try {
                    call.receive<CreateHolidaysRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body: ${e.message}")
                    call.respond(HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid request format.",
                        "details" to e.message
                    ))
                    return@trace
                }

                // Call the service to create the holiday
                val holidayId = service.invoke(request)

                logger.info("Holiday created successfully with ID: $holidayId")

                // On success, respond with 201 Created and the holiday ID
                call.respond(HttpStatusCode.Created, mapOf(
                    "message" to "Holiday created successfully",
                    "holiday_id" to holidayId
                ))

            } catch (e: CfmsException) {
                logger.error("Validation error occurred: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, mapOf(
                    "error" to "Invalid input data",
                    "details" to e.message
                ))

            } catch (e: Exception) {
                logger.error("Internal server error: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, mapOf(
                    "error" to "Holiday creation failed",
                    "details" to "Failed to store holiday in DB, rolling back transaction"
                ))
            }
        }
    }
}
