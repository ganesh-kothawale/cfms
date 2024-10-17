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

    suspend fun listTasks(page:Int , size: Int): ListTasksResponse {
        logger.info("Received request to list tasks: {}", page, size)

        // Convert the request to the domain model using the request mapper
        val domainRequest = requestMapper.toDomain(page, size)

        // Fetch tasks from the domain service
        val tasksResult = listTasks.listTasks(
            page = domainRequest.page,
            size = domainRequest.size
        )

        logger.info("Fetched tasks: {}", tasksResult)

        // Convert the domain result to API response using the response mapper
        return responseMapper.toResponse(
            tasks = tasksResult.data,
            page = domainRequest.page,

            size = domainRequest.size,
            totalPages = calculateTotalPages(tasksResult.totalRecords, domainRequest.size),
            totalRecords = tasksResult.totalRecords
        )
    }

    private fun calculateTotalPages(totalRecords: Int, size: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, size: {}", totalRecords, size)
        return if (totalRecords == 0) 0 else (totalRecords + size - 1) / size
    }
}