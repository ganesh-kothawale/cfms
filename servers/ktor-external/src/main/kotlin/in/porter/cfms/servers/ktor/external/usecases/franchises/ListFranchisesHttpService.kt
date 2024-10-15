package `in`.porter.cfms.servers.ktor.external.usecases.franchises


import `in`.porter.cfms.api.service.franchises.mappers.ListFranchisesRequestMapper
import `in`.porter.cfms.api.service.franchises.usecases.ListFranchisesService
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.TimeoutCancellationException
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListFranchisesHttpService
@Inject
constructor(
    private val franchisesService: ListFranchisesService,
    private val listFranchisesRequestMapper: ListFranchisesRequestMapper
) : Traceable {

    private val logger = LoggerFactory.getLogger(ListFranchisesHttpService::class.java)
    suspend fun listAllHolidays(
        call: ApplicationCall,
        page: Int,
        size: Int
    ) {
        try {
            // Validate page and size
            if (page < 1 || size < 1 || size > 100) {
                logger.error("Invalid page or size: page=$page, size=$size")
                throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
            }

            logger.info("Received request to list all franchises with page: $page and size: $size")

            val request = listFranchisesRequestMapper.toDomain(
                page = page,
                size = size
            )

            // Fetch the paginated list of holidays from the service layer
            val response = franchisesService.listFranchises(request)

            logger.info("Successfully retrieved franchises from the service layer")

            // Check if the response is successful

            // Return the successful response with data
            call.respond(HttpStatusCode.OK, mapOf("data" to response))
        } catch (e: IllegalArgumentException) {
            // Handle invalid page/size parameters
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid page or size parameter",
                            "details" to "Page must be a positive integer, and size must be between 1 and 100."
                        )
                    )
                )
            )
        } catch (e: NoSuchElementException) {
            // Handle cases where no holidays are found
            call.respond(
                HttpStatusCode.NotFound,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "No holidays found",
                            "details" to "No holidays are available for the current request parameters."
                        )
                    )
                )
            )
        } catch (e: TimeoutCancellationException) {
            // Handle timeouts
            call.respond(
                HttpStatusCode.GatewayTimeout,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Holiday service timeout",
                            "details" to "The request to the holiday service timed out. Please try again later."
                        )
                    )
                )
            )
        } catch (e: Exception) {
            // Handle unexpected errors
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Holiday retrieval failed",
                            "details" to "An error occurred while retrieving holiday data from the database."
                        )
                    )
                )
            )
        }
    }
}
