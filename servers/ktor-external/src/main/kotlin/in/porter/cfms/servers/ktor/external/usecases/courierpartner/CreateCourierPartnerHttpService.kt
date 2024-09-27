package `in`.porter.cfms.servers.ktor.external.usecases.courierpartner

import courierpartner.usecases.CreateCourierPartnerService
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierPartner.usecases.internal.CreateCourierPartner
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import javax.inject.Inject

class CreateCourierPartnerHttpService
@Inject
constructor(
    private val service: CreateCourierPartnerService
) : Traceable {

    suspend fun invoke(call: ApplicationCall) = trace {
        try {
            val request = try {
                call.receive<CreateCourierPartnerApiRequest>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf(
                    "error" to "Invalid request format.",
                    "details" to e.message
                ))
                return@trace
            }
            val courierPartnerId = service.invoke(request)
            call.respond(HttpStatusCode.Created, mapOf(
                "message" to courierPartnerId.message,
            ))
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.UnprocessableEntity, e)
        }
    }
}