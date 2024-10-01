package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.service.holidays.usecases.UpdateHolidaysService
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.UpdateHolidaysRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateHolidaysHttpService
@Inject
constructor(
    private val service: UpdateHolidaysService
) {
    private val logger = LoggerFactory.getLogger(UpdateHolidaysHttpService::class.java)

    suspend fun invoke(call: ApplicationCall, holidayId: Long) {
        try {
            logger.info("Received request to update holiday with ID: $holidayId")

            // Deserialize the request
            val request = call.receive<UpdateHolidaysRequest>()

            // Call the service to update the holiday
            val updatedHolidayId = service.invoke(holidayId, request)

            logger.info("Holiday updated successfully with ID: $updatedHolidayId")
            call.respond(mapOf("message" to "Holiday updated successfully", "holiday_id" to updatedHolidayId))

        } catch (e: CfmsException) {
            logger.error("Error occurred while updating holiday: ${e.message}")
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.error("Internal server error: ${e.message}")
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Failed to update holiday"))
        }
    }
}
