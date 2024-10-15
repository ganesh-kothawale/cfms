package `in`.porter.cfms.api.service.franchises.usecases

import `in`.porter.cfms.api.models.franchises.ListFranchisesRequest
import `in`.porter.cfms.api.models.franchises.ListFranchisesResponse
import `in`.porter.cfms.api.service.franchises.mappers.ListFranchisesRequestMapper
import `in`.porter.cfms.api.service.franchises.mappers.ListFranchisesResponseMapper
import `in`.porter.cfms.domain.franchise.usecases.internal.ListFranchises
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListFranchisesService
@Inject
constructor(
    private val listFranchises: ListFranchises,
    private val requestMapper: ListFranchisesRequestMapper
) {

    private val logger = LoggerFactory.getLogger(ListFranchisesService::class.java)

    suspend fun listFranchises(request: ListFranchisesRequest): ListFranchisesResponse {
        logger.info("Received request to list franchises: {}", request)
        // Convert the request using the request mapper
        val domainRequest = requestMapper.toDomain(request.page, request.size)

        // Fetch the franchise data from the domain layer
        val franchisesResult = listFranchises.listFranchises(
            page = domainRequest.page,
            size = domainRequest.size
        )

        logger.info("Fetched franchises: {}", franchisesResult)

        // Return the mapped response using the response mapper
        return ListFranchisesResponseMapper.toResponse(
            franchises = franchisesResult.data,
            page = domainRequest.page,
            size = domainRequest.size,
            totalPages = calculateTotalPages(franchisesResult.totalRecords, domainRequest.size),
            totalRecords = franchisesResult.totalRecords
        )
    }

    private fun calculateTotalPages(totalRecords: Int, pageSize: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, pageSize: {}", totalRecords, pageSize)
        return if (totalRecords == 0) 0 else (totalRecords + pageSize - 1) / pageSize
    }
}
