package `in`.porter.cfms.servers.ktor.usecases

import `in`.porter.cfms.api.service.courierpartner.usecases.RecordCPConnectionService
import `in`.porter.cfms.api.models.cpConnections.RecordCPConnectionApiRequest
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class RecordCPConnectionHttpService
@Inject
constructor(
    private val service: RecordCPConnectionService
) : Traceable {

    companion object: Logging

    suspend fun invoke(call: ApplicationCall) = trace {
        try {
            val request = try {
                call.receive<RecordCPConnectionApiRequest>()
            } catch (e: Exception) {
                logger.error("Failed to convert request body to RecordCPConnectionHttpService: ${e.message}")
                call.respond(
                    HttpStatusCode.BadRequest, mapOf(
                        "error" to "Invalid request format.",
                        "details" to e.message
                    )
                )
                return@trace
            }
            logger.info { "[RecordCPConnectionHttpService] request: $request" }
            service.invoke(request)
                .let { call.respond(HttpStatusCode.OK, it) }
        } catch (e: CfmsException) {
            call.respond(HttpStatusCode.UnprocessableEntity, e)
        }
    }
}
