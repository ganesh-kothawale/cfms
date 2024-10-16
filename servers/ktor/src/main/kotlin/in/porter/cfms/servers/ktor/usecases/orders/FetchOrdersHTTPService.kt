package `in`.porter.cfms.servers.ktor.usecases.orders

import com.google.api.Logging
import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest
import `in`.porter.cfms.api.service.orders.usecases.FetchOrdersApiService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class FetchOrdersHTTPService
@Inject constructor(
    private val fetchOrdersApiService: FetchOrdersApiService
) : Traceable {

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10
                val franchiseId = call.request.queryParameters["franchise_id"]

                FetchOrderApiRequest(page = page, limit = limit, franchiseId = franchiseId)
                    .let { fetchOrdersApiService.invoke(it) }
                    .let { call.respond(HttpStatusCode.OK, mapOf("data" to it)) }

            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request: ${e.message}")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Internal server error: ${e.message}")
            }
        }
    }
}