package `in`.porter.cfms.servers.ktor.usecases.tasks

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.tasks.ListTasksResponse
import `in`.porter.cfms.api.service.tasks.mappers.ListTasksRequestMapper
import `in`.porter.cfms.api.service.tasks.usecases.ListTasksService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasksHttpService @Inject
constructor(
    private val listTasksService: ListTasksService,
    private val listTasksRequestMapper: ListTasksRequestMapper
) : Traceable {
    private val logger = LoggerFactory.getLogger(ListTasksHttpService::class.java)

    suspend fun invoke(
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

            logger.info("Received request to list all tasks with page: $page and size: $size")

            // Use the mapper to create the request model
            val request = listTasksRequestMapper.toDomain(page = page, size = size)
            logger.info("Mapped request for listing tasks: {}", request)

            // Call the service to list tasks
            val tasksResponse: ListTasksResponse = listTasksService.listTasks(page,size)


            // Respond with the formatted result
            call.respond(HttpStatusCode.OK, mapOf("data" to tasksResponse))
            logger.info("Sent response with listed tasks")

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
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to listOf(mapOf("message" to "Failed to retrieve tasks", "details" to e.message))))
        }
    }
}
