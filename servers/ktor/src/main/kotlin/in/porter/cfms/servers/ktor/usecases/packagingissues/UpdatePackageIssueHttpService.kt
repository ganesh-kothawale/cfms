package `in`.porter.cfms.servers.ktor.usecases.packagingissues

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.packageIssue.UpdatePackageIssueRequest
import `in`.porter.cfms.api.service.packageIssue.usecases.UpdatePackageIssueService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdatePackageIssueHttpService @Inject constructor(
    private val updatePackageIssueService: UpdatePackageIssueService
) {

    private val logger = LoggerFactory.getLogger(UpdatePackageIssueHttpService::class.java)

    suspend fun invoke(call: ApplicationCall) {
        try {
            val request = call.receive<UpdatePackageIssueRequest>()
            updatePackageIssueService.invoke(request)

            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "data" to mapOf(
                        "message" to "Task Action updated successfully",
                        "task_id" to request.taskId
                    ),
                    "error" to emptyList<String>()
                )
            )
        } catch (e: NoSuchElementException) {
            logger.error("Not Found", e)
            call.respond(
                HttpStatusCode.NotFound,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Package issue not found",
                            "details" to e.message
                        )
                    )
                )
            )
        } catch (e: CfmsException) {
            logger.error("Update failed: ${e.message}")
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid request format.",
                            "details" to e.message
                        )
                    )
                )
            )
        } catch (e: Exception) {
            logger.error("Unexpected error during package issue update", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Package issue update failed",
                            "details" to "An unexpected error occurred on the server."
                        )
                    )
                )
            )
        }
    }
}
