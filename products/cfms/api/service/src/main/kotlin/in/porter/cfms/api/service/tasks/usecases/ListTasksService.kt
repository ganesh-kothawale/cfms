package `in`.porter.cfms.api.service.tasks.usecases

import `in`.porter.cfms.api.models.tasks.ListTasksRequest
import `in`.porter.cfms.api.models.tasks.ListTasksResponse
import `in`.porter.cfms.api.service.tasks.mappers.ListTasksRequestMapper
import `in`.porter.cfms.api.service.tasks.mappers.ListTasksResponseMapper
import `in`.porter.cfms.domain.tasks.usecases.ListTasks
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListTasksService
@Inject
constructor(
    private val listTasks: ListTasks,  // Domain service to fetch the data
    private val requestMapper: ListTasksRequestMapper,
    private val responseMapper: ListTasksResponseMapper
) {

    private val logger = LoggerFactory.getLogger(ListTasksService::class.java)

    suspend fun listTasks(request: ListTasksRequest): ListTasksResponse {
        logger.info("Received request to list tasks: {}", request)

        // Convert the request to the domain model using the request mapper
        val domainRequest = requestMapper.toDomain(request.page, request.limit)

        // Fetch tasks from the domain service
        val tasksResult = listTasks.listTasks(
            page = domainRequest.page,
            limit = domainRequest.limit
        )

        logger.info("Fetched tasks: {}", tasksResult)

        // Convert the domain result to API response using the response mapper
        return responseMapper.toResponse(
            tasks = tasksResult.data,
            page = domainRequest.page,
            limit = domainRequest.limit,
            totalPages = calculateTotalPages(tasksResult.totalRecords, domainRequest.limit),
            totalRecords = tasksResult.totalRecords
        )
    }

    private fun calculateTotalPages(totalRecords: Int, limit: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, limit: {}", totalRecords, limit)
        return if (totalRecords == 0) 0 else (totalRecords + limit - 1) / limit
    }
}