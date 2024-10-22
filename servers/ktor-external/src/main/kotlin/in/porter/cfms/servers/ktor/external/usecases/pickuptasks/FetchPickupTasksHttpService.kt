package `in`.porter.cfms.servers.ktor.external.usecases.pickuptasks

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsRequest
import `in`.porter.cfms.api.models.pickupTasks.FetchPickupTasksRequest
import `in`.porter.cfms.api.models.pickupTasks.PickupTasksResponse
import `in`.porter.cfms.api.service.hlp.usecases.FetchHlpRecordsService
import `in`.porter.cfms.api.service.pickupTasks.mappers.FetchPickupTasksRequestMapper
import `in`.porter.cfms.api.service.pickupTasks.usecases.FetchPickupTasksService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import org.slf4j.LoggerFactory
import javax.inject.Inject

class FetchPickupTasksHttpService
@Inject
constructor(
    private val service: FetchPickupTasksService,
    private val fetchPickupTasksRequestMapper: FetchPickupTasksRequestMapper
) : Traceable {
    companion object : Logging

    private val logger = LoggerFactory.getLogger(FetchPickupTasksHttpService::class.java)

    suspend fun invoke(
        call: ApplicationCall,
        page: Int,
        size: Int
    ) {
        trace {
            try {
                if (page < 1 || size < 1 || size > 100) {
                    logger.error("Invalid page or size: page=$page, size=$size")
                    throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
                }
                val pickupTasksResponse: PickupTasksResponse = service.invoke(page, size)
                call.respond(HttpStatusCode.OK, mapOf("data" to pickupTasksResponse))

            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf(
                        "error" to listOf(
                            mapOf(
                                "message" to "Invalid page or limit parameter",
                                "details" to "Page must be a positive integer, and limit must be between 1 and 100."
                            )
                        )
                    )
                )
            } catch (e: CfmsException) {
                // Handle any validation or service-related exceptions
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to e.message))))
            } catch (e: Exception) {
                // Handle any unexpected errors
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to listOf(mapOf("message" to "Failed to retrieve tasks", "details" to e.message)))
                )
            }
        }
    }
}