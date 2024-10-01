package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import `in`.porter.cfms.api.service.holidays.usecases.UpdateHolidaysService
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.inject.Inject

class UpdateHolidaysHttpService
@Inject
constructor(
    private val service: UpdateHolidaysService
) : Traceable {
    private val logger = LoggerFactory.getLogger(UpdateHolidaysHttpService::class.java)

    suspend fun invoke(call: ApplicationCall, holidayId: Int) {
        trace {
            try {
                logger.info("Received request to update holiday with ID: $holidayId")

                val rawRequest = call.receive<Map<String, Any>>()
                logger.info("Requested Body before serialization : {}", rawRequest)
                // Validate franchise_id
                val franchiseId = rawRequest["franchise_id"]?.toString()
                if (franchiseId.isNullOrEmpty()) {
                    logger.error("Franchise ID is missing or empty")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Franchise ID is required and cannot be empty."
                        )
                    )
                    return@trace
                }

                // Validate start_date and end_date
                val startDateString = rawRequest["start_date"]?.toString()
                if (startDateString.isNullOrEmpty()) {
                    logger.error("Start date is required.")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid input data",
                            "details" to "Start date is required."
                        )
                    )
                    return@trace
                }

                val endDateString = rawRequest["end_date"]?.toString()
                if (endDateString.isNullOrEmpty()) {
                    logger.error("End date is required.")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid input data",
                            "details" to "End date is required."
                        )
                    )
                    return@trace
                }

                try {
                    LocalDate.parse(startDateString)
                } catch (e: DateTimeParseException) {
                    logger.error("Invalid start date format: $startDateString")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid start date format.",
                            "details" to "The date '$startDateString' is invalid."
                        )
                    )
                    return@trace
                }

                try {
                    LocalDate.parse(endDateString)
                } catch (e: DateTimeParseException) {
                    logger.error("Invalid end date format: $endDateString")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid end date format.",
                            "details" to "The date '$endDateString' is invalid."
                        )
                    )
                    return@trace
                }

                // Validate leave_type
                val leaveTypeString = rawRequest["leave_type"]?.toString()
                if (leaveTypeString.isNullOrEmpty()) {
                    logger.error("Leave type is missing")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Leave type is required."
                        )
                    )
                    return@trace
                }

                try {
                    LeaveType.valueOf(leaveTypeString)
                } catch (e: IllegalArgumentException) {
                    val validValues = LeaveType.entries.joinToString(", ") { it.name }
                    logger.error("Invalid leave type: $leaveTypeString. Expected one of: $validValues")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid leave type. Valid values are: [$validValues]"
                        )
                    )
                    return@trace
                }

                // Validate backup_franchise_ids (optional, but should not be empty if present)
                val backupFranchiseIds = rawRequest["backup_franchise_ids"]?.toString()
                if (backupFranchiseIds != null && backupFranchiseIds.isBlank()) {
                    logger.error("Backup franchise IDs cannot be empty if provided")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Backup franchise IDs cannot be empty if provided."
                        )
                    )
                    return@trace
                }

                // Now that we've validated the input, deserialize the full request
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
                service.invoke(holidayId, request)

                logger.info("Holiday updated successfully")
                call.respond(HttpStatusCode.OK, mapOf("message" to "Holiday updated successfully"))

            } catch (e: CfmsException) {
                logger.error("Error occurred while updating holiday: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                logger.error("Internal server error: ${e.message}")
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Failed to update holiday"))
            }
        }
    }
}
