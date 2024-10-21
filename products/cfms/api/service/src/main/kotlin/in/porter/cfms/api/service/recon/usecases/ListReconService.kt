package `in`.porter.cfms.api.service.recon.usecases

import `in`.porter.cfms.api.models.recon.ListReconResponse
import `in`.porter.cfms.api.service.recon.mappers.ListReconRequestMapper
import `in`.porter.cfms.api.service.recon.mappers.ListReconResponseMapper
import `in`.porter.cfms.domain.recon.usecases.ListRecon
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListReconService
@Inject
constructor(
    private val listRecon: ListRecon,  // Domain service to fetch recon data
    private val requestMapper: ListReconRequestMapper,
    private val responseMapper: ListReconResponseMapper
) {

    private val logger = LoggerFactory.getLogger(ListReconService::class.java)

    suspend fun listRecon(page: Int, size: Int): ListReconResponse {
        logger.info("Received request to list recon: page = {}, size = {}", page, size)

        // Convert the request to the domain model using the request mapper
        val domainRequest = requestMapper.toDomain(page, size)

        // Fetch recons from the domain service
        val reconResult = listRecon.listRecon(
            page = domainRequest.page,
            size = domainRequest.size
        )

        logger.info("Fetched recon records: {}", reconResult)

        // Convert the domain result to API response using the response mapper
        return responseMapper.toResponse(
            recons = reconResult.data,
            page = domainRequest.page,
            size = domainRequest.size,
            totalPages = calculateTotalPages(reconResult.totalRecords, domainRequest.size),
            totalRecords = reconResult.totalRecords
        )
    }

    private fun calculateTotalPages(totalRecords: Int, size: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, size: {}", totalRecords, size)
        return if (totalRecords == 0) 0 else (totalRecords + size - 1) / size
    }
}
