package `in`.porter.cfms.servers.ktor.external.usecases.recon

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.recon.ListReconResponse
import `in`.porter.cfms.api.service.recon.mappers.ListReconRequestMapper
import `in`.porter.cfms.api.service.recon.usecases.ListReconService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListReconHttpService
@Inject
constructor(
    private val listReconService: ListReconService,
    private val listReconRequestMapper: ListReconRequestMapper
) : Traceable {
    private val logger = LoggerFactory.getLogger(ListReconHttpService::class.java)

    suspend fun invoke(
        call: ApplicationCall,
        page: Int,
        size: Int
    ) {
        try {
            // Validate page and size
            if (page < 1 || size < 1 || size > 100) {
                logger.error("Invalid page or size: page=$page, size=$size")
                throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
            }

            logger.info("Received request to list all recon entries with page: $page and size: $size")

            // Use the mapper to create the request model
            val request = listReconRequestMapper.toDomain(page = page, size = size)
            logger.info("Mapped request for listing recon entries: {}", request)

            // Call the service to list recon entries
            val reconResponse: ListReconResponse = listReconService.listRecon(page, size)

            // Respond with the formatted result
            call.respond(HttpStatusCode.OK, mapOf("data" to reconResponse))
            logger.info("Sent response with listed recon entries")

        } catch (e: IllegalArgumentException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid page or size parameter",
                            "details" to "Page must be a positive integer, and size must be between 1 and 100."
                        )
                    )
                )
            )
        } catch (e: CfmsException) {
            // Handle any validation or service-related exceptions
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to e.message))))
        } catch (e: Exception) {
            // Handle any unexpected errors
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to listOf(mapOf("message" to "Failed to retrieve recon entries", "details" to e.message))))
        }
    }
}
