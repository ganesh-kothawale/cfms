package `in`.porter.cfms.servers.ktor.usecases.packagingissues

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueRequest
import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueResponse
import `in`.porter.cfms.api.service.packageIssue.usecases.ListPackageIssueService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PackageIssuesHttpService
@Inject
constructor(
    private val listPackagingIssueService: ListPackageIssueService
) {

    private val logger = LoggerFactory.getLogger(PackageIssuesHttpService::class.java)

    suspend fun invoke(
        call: ApplicationCall,
        request : ListPackageIssueRequest
    ) {
        try {
            // Validate page and size parameters
            if (request.page < 1 || request.size < 1 || request.size > 100) {
                logger.error("Invalid page or size: page=$request.page, size=$request.size")
                throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
            }

            logger.info("Received request to list all packaging issues with page: $request.page and size: $request.size")

            // Use the recon service but filter for return_requested = true
            val packageIssueResponse: ListPackageIssueResponse = listPackagingIssueService.invoke(request)

            // Respond with the filtered result
            call.respond(HttpStatusCode.OK, mapOf("data" to packageIssueResponse))
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
