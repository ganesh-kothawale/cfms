package `in`.porter.cfms.servers.ktor.usecases.cpConnection

import `in`.porter.cfms.api.service.courierpartner.usecases.FetchCPConnectionsService
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiResponse
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.call
import io.ktor.server.response.*
import io.ktor.server.util.*
import org.apache.logging.log4j.kotlin.Logging
import java.time.LocalDate
import javax.inject.Inject

class FetchCPConnectionHttpService
@Inject
constructor(
    private val service: FetchCPConnectionsService
) : Traceable {

    companion object : Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val request = try {
                    val page = call.request.queryParameters["page"]?.toInt() ?: 1
                    val size = call.request.queryParameters["size"]?.toInt() ?: 10
                    val createdDate = call.request.queryParameters["created_date"]?.let { LocalDate.parse(it) }
                    val updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDate.parse(it) }
                    val courierPartners = call.request.queryParameters["courier_partner"]?.split(",")?.map { it.trim() }
                    val franchiseIds = call.request.queryParameters["franchise_id"]?.split(",")?.map { it.trim() }

                    FetchCPConnectionsApiRequest(page, size, createdDate, updatedDate, courierPartners, franchiseIds)
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to FetchCPConnectionHttpService: ${e.message}")

                    call.respond(
                        HttpStatusCode.BadRequest, mapOf(
                            "error" to listOf(
                                mapOf(
                                    "message" to "Invalid page or size parameter",
                                    "details" to "Page must be a positive integer, and size must be between 1 and 100."
                                )
                            )
                        )
                    )
                    return@trace
                }

                val response = service.invoke(request)
                if (response is FetchCPConnectionsApiResponse.Error) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to response.error))
                } else {
                    call.respond(HttpStatusCode.OK, mapOf("data" to response))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.UnprocessableEntity, e)
            }
        }
    }
}
