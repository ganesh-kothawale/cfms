package `in`.porter.cfms.api.service.recon.usecases

import `in`.porter.cfms.api.models.recon.FetchReconResponse
import `in`.porter.cfms.api.models.recon.ListReconResponse
import `in`.porter.cfms.api.models.recon.ReconTaskResponse
import `in`.porter.cfms.api.service.recon.mappers.FetchReconTasksResponseMapper
import `in`.porter.cfms.api.service.recon.mappers.ListReconRequestMapper
import `in`.porter.cfms.api.service.recon.mappers.ListReconResponseMapper
import `in`.porter.cfms.domain.recon.usecases.FetchReconTask
import `in`.porter.cfms.domain.recon.usecases.ListRecon
import org.slf4j.LoggerFactory
import javax.inject.Inject

class FetchReconTasksService@Inject
constructor(
    private val fetchRecon: FetchReconTask,
    private val requestMapper: ListReconRequestMapper,
    private val responseMapper: FetchReconTasksResponseMapper
) {

    private val logger = LoggerFactory.getLogger(FetchReconTasksService::class.java)

    suspend fun invoke(page: Int, size: Int): FetchReconResponse {
        logger.info("Received request to list recon: page = {}, size = {}", page, size)

        val domainRequest = requestMapper.toDomain(page, size)

        val reconResult = fetchRecon.invoke(
            page = domainRequest.page,
            size = domainRequest.size,
        )

        logger.info("Fetched recon records: {}", reconResult)

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