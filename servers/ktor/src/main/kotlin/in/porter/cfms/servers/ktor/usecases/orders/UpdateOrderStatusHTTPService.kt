package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.api.models.orders.UpdateOrderStatusApiRequest
import `in`.porter.cfms.api.service.orders.usecases.UpdateOrderStatusApiService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.instrumentation.opentracing.trace
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class UpdateOrderStatusHTTPService
@Inject constructor(
    private val service: UpdateOrderStatusApiService
) : Traceable {

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val orderId = call.request.queryParameters["recordId"]
                call.receive<UpdateOrderStatusApiRequest>()
                    .let { service.invoke(it, orderId) }
                    .let { call.respond(HttpStatusCode.OK, it) }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request: ${e.message}")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Internal server error: ${e.message}")
            }
        }
    }
}