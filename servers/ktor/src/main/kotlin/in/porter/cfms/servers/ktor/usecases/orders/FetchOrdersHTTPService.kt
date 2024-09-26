package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest
import `in`.porter.cfms.api.models.orders.FetchOrderResponse
import `in`.porter.cfms.api.service.orders.usecases.FetchOrdersApiService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import io.ktor.http.HttpStatusCode // Correct import
import javax.inject.Inject

class FetchOrdersHTTPService @Inject constructor(
    private val fetchOrdersApiService: FetchOrdersApiService
) : Traceable {

    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                call.receive<FetchOrderApiRequest>()
                    .let { fetchOrdersApiService.invoke(it) }
                    .let { call.respond(HttpStatusCode.OK, it) }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request: ${e.message}")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Internal server error: ${e.message}")
            }
        }
    }
}