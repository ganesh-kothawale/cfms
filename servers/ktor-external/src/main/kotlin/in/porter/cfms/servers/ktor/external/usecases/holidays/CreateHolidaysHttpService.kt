package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.CreateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.CreateHolidaysService
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeParseException
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

                // Manually handle the date and leave_type validation before deserializing the request
                val rawRequest = call.receive<Map<String, Any>>()

                // Validate start_date and end_date
                val startDateString = rawRequest["start_date"]?.toString()
                val endDateString = rawRequest["end_date"]?.toString()

                try {
                    LocalDate.parse(startDateString)
                } catch (e: DateTimeParseException) {
                    logger.error("Invalid start date format: $startDateString")
                    call.respond(HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid start date format.",
                        "details" to "The date '$startDateString' is invalid."
                    ))
                    return@trace
                }

                try {
                    LocalDate.parse(endDateString)
                } catch (e: DateTimeParseException) {
                    logger.error("Invalid end date format: $endDateString")
                    call.respond(HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid end date format.",
                        "details" to "The date '$endDateString' is invalid."
                    ))
                    return@trace
                }

                // Validate leave_type
                val leaveTypeString = rawRequest["leave_type"]?.toString()
                try {
                    LeaveType.valueOf(leaveTypeString ?: "")
                } catch (e: IllegalArgumentException) {
                    val validValues = LeaveType.values().joinToString(", ") { it.name }
                    logger.error("Invalid leave type: $leaveTypeString. Expected one of: $validValues")
                    call.respond(HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid leave type. Valid values are: [$validValues]"
                    ))
                    return@trace
                }

                // Now that we've validated the date and leave type, deserialize the rest of the request
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
