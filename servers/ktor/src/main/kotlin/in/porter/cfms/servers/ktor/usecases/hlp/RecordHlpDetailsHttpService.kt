package `in`.porter.cfms.servers.ktor.usecases.hlp

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest
import `in`.porter.cfms.api.models.tasks.UpdateTaskStatusRequest
import `in`.porter.cfms.api.service.hlp.usecases.RecordHlpDetailsService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class RecordHlpDetailsHttpService
@Inject
constructor(
    private val service: RecordHlpDetailsService
) : Traceable {

    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val request = try {
                    call.receive<RecordHlpDetailsRequest>()
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to RecordHlpDetailsRequest: ${e.message}")
                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to "Invalid request parameters.",
                            "details" to e.message
                        )
                    )
                    return@trace
                }
                logger.info { "[RecordHlpDetailsHttpService] request: $request" }
                service.invoke(request)
                    .let { call.respond(HttpStatusCode.OK, it) }
            } catch (e: CfmsException) {
                call.respond(HttpStatusCode.UnprocessableEntity, e)
            }
        }

    }
}
