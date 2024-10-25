package `in`.porter.cfms.servers.ktor.usecases.franchises

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.service.franchises.usecases.CreateFranchiseRecordService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class CreateFranchiseRecordHttpService
@Inject
constructor(
    private val service: CreateFranchiseRecordService
) : Traceable {
    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                logger.info("Received request to create a franchise record")
                val request = try {
                    call.receive<RecordFranchiseDetailsRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body: ${e.message}")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid request format.",
                            "details" to e.message
                        )
                    )
                    return@trace
                }
                service.invoke(request)
                    .let { call.respond(HttpStatusCode.OK, it) }
            } catch (e: CfmsException) {
                logger.error("Validation error occurred: ${e.message}")
                call.respond(
                    HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid input data",
                        "details" to e.message
                    )
                )

            } catch (e: Exception) {
                logger.error("Internal server error: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError, mapOf(
                        "error" to "Franchise creation failed",
                        "details" to "Failed to store holiday in DB, rolling back transaction"
                    )
                )
            }
        }
    }
}
