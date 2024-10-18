package `in`.porter.cfms.servers.ktor.usecases

import `in`.porter.cfms.api.service.courierpartner.usecases.RecordCPConnectionService
import `in`.porter.cfms.api.models.cpConnections.RecordCPConnectionApiRequest
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class RecordCPConnectionHttpService
@Inject
constructor(
    private val service: RecordCPConnectionService
) : Traceable {

    suspend fun invoke(call: ApplicationCall) = trace {
        try {
            val request = try {
                call.receive<RecordCPConnectionApiRequest>()
            } catch (e: Exception) {
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
            call.respond(HttpStatusCode.UnprocessableEntity, e)
        }
    }
}
