package `in`.porter.cfms.servers.ktor.usecases.packagingissues

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.recon.ListReconResponse
import `in`.porter.cfms.api.service.recon.mappers.ListReconRequestMapper
import `in`.porter.cfms.api.service.recon.usecases.ListReconService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PackagingIssuesHttpService
@Inject
constructor(
    private val listReconService: ListReconService,
    private val listReconRequestMapper: ListReconRequestMapper
) {

    private val logger = LoggerFactory.getLogger(PackagingIssuesHttpService::class.java)

    suspend fun invoke(
        call: ApplicationCall,
        page: Int,
        size: Int
    ) {
        try {
            // Validate page and size parameters
            if (page < 1 || size < 1 || size > 100) {
                logger.error("Invalid page or size: page=$page, size=$size")
                throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
            }

            logger.info("Received request to list all packaging issues with page: $page and size: $size")

            // Use the recon service but filter for packaging_required = true
            val reconResponse: ListReconResponse = listReconService.listRecon(page, size, packagingRequired = true)

            // Respond with the filtered result
            call.respond(HttpStatusCode.OK, mapOf("data" to reconResponse))
            logger.info("Sent response with listed packaging issues")

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
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to e.message))))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to listOf(mapOf("message" to "Failed to retrieve packaging issues", "details" to e.message))))
        }
    }
}
