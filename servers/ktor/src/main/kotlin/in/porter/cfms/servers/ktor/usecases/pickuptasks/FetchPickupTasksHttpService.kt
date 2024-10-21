package `in`.porter.cfms.servers.ktor.usecases.pickuptasks

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsRequest
import `in`.porter.cfms.api.models.pickupTasks.FetchPickupTasksRequest
import `in`.porter.cfms.api.service.hlp.usecases.FetchHlpRecordsService
import `in`.porter.cfms.api.service.pickupTasks.usecases.FetchPickupTasksService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class FetchPickupTasksHttpService
@Inject
constructor(
    private val service: FetchPickupTasksService
) : Traceable {
    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val request = try {
                    call.receive<FetchPickupTasksRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to FetchPickupTasksRequest: ${e.message}")
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf(
                            "error" to listOf(
                                mapOf(
                                    "message" to "Invalid request parameters",
                                    "details" to listOf(
                                        "page" to "Page number must be a positive integer.",
                                        "size" to "Size must be a positive integer."
                                    )
                                )
                            )
                        )
                    )
                    return@trace
                }
                service.invoke(request.page, request.size)
                    .let { call.respond(HttpStatusCode.OK, it) }
            } catch (e: CfmsException) {
                call.respond(HttpStatusCode.UnprocessableEntity, e)
            }
        }
    }
}