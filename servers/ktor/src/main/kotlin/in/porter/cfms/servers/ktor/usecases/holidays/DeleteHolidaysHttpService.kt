package `in`.porter.cfms.servers.ktor.usecases.holidays


import `in`.porter.cfms.api.service.holidays.usecases.DeleteHolidaysService
import `in`.porter.cfms.domain.exceptions.CfmsException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteHolidaysHttpService @Inject constructor(
    private val deleteHolidaysService: DeleteHolidaysService
) {
    private val logger = LoggerFactory.getLogger(DeleteHolidaysHttpService::class.java)

    suspend fun invoke(call: ApplicationCall, holidayId: String) {
        try {
            logger.info("Received request to delete holiday with ID: {}", holidayId)
            deleteHolidaysService.invoke(holidayId)
            call.respond(
                HttpStatusCode.Created, mapOf(
                    "data" to mapOf(
                        "message" to "Holidays deleted successfully"
                    ),
                    "error" to emptyList<String>()
                )
            )

        } catch (e: CfmsException) {
            logger.error("Validation error occurred: {}", e.message)
            // Return 400 with structured error message
            call.respond(
                HttpStatusCode.BadRequest, mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid input data",
                            "details" to "Holiday with ID $holidayId not found"
                        )
                    )
                )
            )

        } catch (e: Exception) {
            logger.error("Unexpected error occurred while deleting holiday: {}", e.message)
            // Return 500 with structured error message
            call.respond(
                HttpStatusCode.InternalServerError, mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Failed to delete holiday",
                            "details" to "Error deleting leave: ${e.message}"
                        )
                    )
                )
            )
        }
    }
}