package `in`.porter.cfms.api.service.pickupTasks.usecases

import `in`.porter.cfms.api.models.pickupTasks.PickupTasksResponse
import `in`.porter.cfms.api.service.pickupTasks.mappers.FetchPickupTasksRequestMapper
import `in`.porter.cfms.api.service.pickupTasks.mappers.FetchPickupTasksResponseMapper
import `in`.porter.cfms.domain.pickuptasks.usecases.internal.FetchPickupTasks
import org.slf4j.LoggerFactory

import javax.inject.Inject

class FetchPickupTasksService @Inject
constructor(
    private val fetchPickupTasks: FetchPickupTasks,
    private val requestMapper: FetchPickupTasksRequestMapper,
    private val responseMapper: FetchPickupTasksResponseMapper
) {
    private val logger = LoggerFactory.getLogger(FetchPickupTasksService::class.java)
    suspend fun invoke(page:Int , size: Int): PickupTasksResponse {
        logger.info("Received request to list pickupTasks: {}", page, size)

        val domainRequest = requestMapper.toDomain(page, size)

        val pickupTasksResult = fetchPickupTasks.invoke(
            page = domainRequest.page,
            size = domainRequest.size
        )

        logger.info("Fetched pickupTasks: {}", pickupTasksResult)

        return responseMapper.toResponse(
            pickupTasks = pickupTasksResult.data,
            page = domainRequest.page,
            size = domainRequest.size,
            totalPages = calculateTotalPages(pickupTasksResult.totalRecords, domainRequest.size),
            totalRecords = pickupTasksResult.totalRecords
        )
    }
    
    private fun calculateTotalPages(totalRecords: Int, size: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, size: {}", totalRecords, size)
        return if (totalRecords == 0) 0 else (totalRecords + size - 1) / size
    }
}