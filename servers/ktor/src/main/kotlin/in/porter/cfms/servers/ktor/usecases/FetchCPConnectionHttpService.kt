package `in`.porter.cfms.servers.ktor.usecases

import `in`.porter.cfms.api.service.courierpartner.usecases.FetchCPConnectionsService
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest
import `in`.porter.cfms.domain.exceptions.CfmsException
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

  suspend fun invoke(call: ApplicationCall) = trace {
    try {
      val request = try {
        call.receive<FetchCPConnectionsApiRequest>()
      } catch (e: CfmsException) {
        logger.info("error in request")

        call.respond(
          HttpStatusCode.BadRequest, mapOf(
            "error" to "Invalid request format.",
            "details" to e.message
          )
        )
        return@trace
      }
      logger.info("executed")

      val response = service.invoke(request)
      call.respond(HttpStatusCode.OK, response)

    } catch (e: CfmsException) {
      call.respond(HttpStatusCode.UnprocessableEntity, e)
    }
  }
}
