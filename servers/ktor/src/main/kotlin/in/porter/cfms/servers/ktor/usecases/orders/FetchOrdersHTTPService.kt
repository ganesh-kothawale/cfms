package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest
import `in`.porter.cfms.api.service.orders.usecases.FetchOrdersApiService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.instrumentation.opentracing.trace
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import software.amazon.awssdk.http.HttpStatusCode
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
                    .let { call.respond(HttpStatusCode.OK) }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BAD_REQUEST)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.INTERNAL_SERVER_ERROR)
            }
        }
    }
}