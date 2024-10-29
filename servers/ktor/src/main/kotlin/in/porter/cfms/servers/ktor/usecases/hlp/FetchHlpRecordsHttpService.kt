package `in`.porter.cfms.servers.ktor.usecases.hlp

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsRequest
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsResponse
import `in`.porter.cfms.api.service.hlp.usecases.FetchHlpRecordsService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.util.*
import org.apache.logging.log4j.kotlin.Logging
import java.time.LocalDate
import javax.inject.Inject

class FetchHlpRecordsHttpService
@Inject
constructor(
    private val service: FetchHlpRecordsService
) : Traceable {

    companion object: Logging

    suspend fun invoke(call: ApplicationCall) {
        trace {
            try {
                val request = try {
                    val page = call.request.queryParameters["page"]?.toInt() ?: 1
                    val size = call.request.queryParameters["size"]?.toInt() ?: 10
                    val createdDate = call.request.queryParameters["created_date"]?.let { LocalDate.parse(it) }
                    val updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDate.parse(it) }
                    val driverNames = call.request.queryParameters["driver_name"]?.split(",")?.map { it.trim() }
                    val driverNumber = call.request.queryParameters["driver_number"]
                    val hlpOrderIds = call.request.queryParameters["hlp_order_id"]?.split(",")?.map { it.trim() }
                    val hlpOrderStatus = call.request.queryParameters["hlp_order_status"]
                    val franchiseIds = call.request.queryParameters["franchise_ids"]?.split(",")?.map { it.trim() }
                    val vehicleTypes = call.request.queryParameters["vehicle_type"]?.split(",")?.map { it.trim() }

                    FetchHlpRecordsRequest(page, size, createdDate, updatedDate, driverNames, driverNumber, hlpOrderIds, hlpOrderStatus, franchiseIds, vehicleTypes)
                } catch (e: Exception) {
                    logger.error("Failed to convert request body to FetchHlpRecordsRequest: ${e.message}")
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf(
                            "error" to listOf(
                                mapOf(
                                    "message" to "Invalid request parameters",
                                    "details" to "page and size must be positive integers"
                                )
                            )
                        )
                    )
                    return@trace
                }
                service.invoke(request)
                    .let {
                        if (it is FetchHlpRecordsResponse.Error) {
                            call.respond(HttpStatusCode.BadRequest, mapOf("error" to it.error))
                        } else {
                            call.respond(HttpStatusCode.OK, mapOf("data" to it))
                        }
                    }
            } catch (e: CfmsException) {
                call.respond(HttpStatusCode.UnprocessableEntity, e)
            }
        }
    }
}
