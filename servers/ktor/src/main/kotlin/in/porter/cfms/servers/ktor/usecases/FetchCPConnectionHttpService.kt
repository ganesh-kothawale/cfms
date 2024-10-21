package `in`.porter.cfms.servers.ktor.usecases

import `in`.porter.cfms.api.service.courierpartner.usecases.FetchCPConnectionsService
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class FetchCPConnectionHttpService
@Inject
constructor(
    private val service: FetchCPConnectionsService
) : Traceable {

    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val request = try {
                    call.receive<FetchCPConnectionsApiRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to FetchCPConnectionHttpService: ${e.message}")

                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to listOf(
                                mapOf(
                                    "message" to "Invalid page or size parameter",
                                    "details" to "Page must be a positive integer, and size must be between 1 and 100."
                                )
                            )
                        )
                    )
                    return@trace
                }

                val response = service.invoke(request)
                call.respond(HttpStatusCode.OK, mapOf("data" to response))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.UnprocessableEntity, e)
            }
        }
    }
}
