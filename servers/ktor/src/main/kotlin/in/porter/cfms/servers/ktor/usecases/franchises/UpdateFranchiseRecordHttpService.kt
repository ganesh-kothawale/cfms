package `in`.porter.cfms.servers.ktor.usecases.franchises

import `in`.porter.cfms.api.models.franchises.FranchiseResponse
import `in`.porter.cfms.api.models.franchises.ErrorResponse
import `in`.porter.cfms.api.models.franchises.Data
import `in`.porter.cfms.api.models.franchises.UpdateFranchiseRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.franchises.usecases.UpdateFranchiseDetailsService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateFranchiseRecordHttpService
@Inject
constructor(
    private val service: UpdateFranchiseDetailsService
) : Traceable {
    private val logger = LoggerFactory.getLogger(UpdateFranchiseRecordHttpService::class.java)

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val rawJson = call.receiveText()
                logger.info("Received raw JSON: $rawJson")

                val request = try {
                    call.receive<UpdateFranchiseRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to parse JSON: ${e.message}")
                    val errorResponse = FranchiseResponse(
                        error = listOf(
                            ErrorResponse(
                                message = "Invalid request body",
                                details = e.message ?: "Unknown error"
                            )
                        )
                    )
                    call.respond(HttpStatusCode.BadRequest, errorResponse)
                    return@trace
                }

                logger.info("Parsed request: $request")


                val response = service.invoke(request)

                // If successful, return 200 OK with a franchise ID
                if (response != null) {
                    if (response > 0) {
                        val successResponse = FranchiseResponse(
                            data = Data(
                                message = "Franchise updated successfully",
                                franchise_id = request.franchiseId
                            )
                        )
                        call.respond(HttpStatusCode.OK, successResponse)
                    } else {
                        val errorResponse = FranchiseResponse(
                            error = listOf(
                                ErrorResponse(
                                    message = "Franchise update failed",
                                    details = "Unknown error"
                                )
                            )
                        )
                        call.respond(HttpStatusCode.InternalServerError, errorResponse)
                    }
                }

            } catch (e: CfmsException) {
                logger.error("Error updating franchise: ${e.message}")
                val errorResponse = FranchiseResponse(
                    error = listOf(
                        ErrorResponse(
                            message = e.message ?: "Franchise update failure",
                            details = "Franchise update failure details"
                        )
                    )
                )
                call.respond(HttpStatusCode.BadRequest, errorResponse)

            } catch (e: Exception) {
                logger.error("Unexpected error: ${e.message}")
                val errorResponse = FranchiseResponse(
                    error = listOf(
                        ErrorResponse(
                            message = "Franchise update failed",
                            details = e.message ?: "Unknown error"
                        )
                    )
                )
                call.respond(HttpStatusCode.InternalServerError, errorResponse)
            }
        }
    }
}
