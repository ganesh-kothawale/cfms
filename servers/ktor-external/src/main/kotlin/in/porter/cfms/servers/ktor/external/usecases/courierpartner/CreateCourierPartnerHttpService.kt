package `in`.porter.cfms.servers.ktor.external.usecases.courierpartner

import courierpartner.usecases.CreateCourierPartnerService
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
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
            call.receive<CreateCourierPartnerApiRequest>()
                .let { service.invoke(it) }
                .let { call.respond(HttpStatusCode.OK, it) }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.UnprocessableEntity, e)
        }
    }
}