package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
import `in`.porter.cfms.api.service.orders.usecases.CreateOrderService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.instrumentation.opentracing.trace
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import software.amazon.awssdk.http.HttpStatusCode
import javax.inject.Inject

class CreateOrderHTTPService
@Inject
constructor(
    private val orderApiService:CreateOrderService ,
) : Traceable {

    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                call.receive<CreateOrderApiRequest>()
                    .also { logger.info { "Request payload for CreateOrder: $it" } }
                    .let { orderApiService.invoke(it) }
                    .let { call.respond(HttpStatusCode.OK) }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BAD_REQUEST)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.INTERNAL_SERVER_ERROR)
            }
        }
    }
}