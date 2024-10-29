package `in`.porter.cfms.servers.ktor.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import `in`.porter.cfms.api.service.holidays.mappers.ListHolidaysRequestMapper
import `in`.porter.cfms.api.service.holidays.usecases.ListHolidaysService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class ListHolidaysHttpService @Inject
constructor(
    private val listHolidaysService: ListHolidaysService
) : Traceable {
    private val logger = LoggerFactory.getLogger(ListHolidaysHttpService::class.java)

    suspend fun invoke(
        call: ApplicationCall,
        request: ListHolidaysRequest
    ) {
        try {

            if (request.page < 1 || request.size < 1 || request.size > 100) {
                logger.error("Invalid page or size: page=${request.page}, size=${request.size}")
                throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
            }

            logger.info("Received request to list all holidays")


            // Call the service to list holidays
            val holidaysResponse = listHolidaysService.invoke(request)

            // Respond with the formatted result
            call.respond(HttpStatusCode.OK, mapOf("data" to holidaysResponse))
            logger.info("Sent response to list all holidays")
        } catch (e: CfmsException) {
            // Handle validation or service-related exceptions
            call.respond(HttpStatusCode.BadRequest, mapOf(
                "error" to listOf(
                    mapOf(
                        "message" to "Invalid request parameters",
                        "details" to e.message
                    )
                )
            ))
        } catch (e: Exception) {
            // Handle unexpected errors
            logger.error("Unexpected error: ${e.message}", e)
            call.respond(HttpStatusCode.InternalServerError, mapOf(
                "error" to listOf(
                    mapOf(
                        "message" to "Failed to retrieve holidays",
                        "details" to e.message
                    )
                )
            ))
        }
    }
}