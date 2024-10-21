package `in`.porter.cfms.servers.ktor.usecases.hlp

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest
import `in`.porter.cfms.api.models.hlp.UpdateHlpDetailsRequest
import `in`.porter.cfms.api.service.hlp.usecases.UpdateHlpDetailsService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class UpdateHlpDetailsHttpService
@Inject
constructor(
    private val service: UpdateHlpDetailsService
) : Traceable {

    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val request = try {
                    call.receive<UpdateHlpDetailsRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to UpdateHlpDetailsRequest: ${e.message}")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid request parameters.",
                            "details" to e.message
                        )
                    )
                    return@trace
                }
                logger.info { "[UpdateHlpDetailsHttpService] request: $request" }
                service.invoke(request)
                    .let { call.respond(HttpStatusCode.OK, it) }
            } catch (e: CfmsException) {
                call.respond(HttpStatusCode.UnprocessableEntity, e)
            }
        }

    }
}
