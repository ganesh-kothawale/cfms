package `in`.porter.cfms.api.service.packageIssue.usecases

import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueResponse
import `in`.porter.cfms.api.service.packageIssue.mappers.ListPackageIssueRequestMapper
import `in`.porter.cfms.api.service.packageIssue.mappers.ListPackageIssueResponseMapper
import `in`.porter.cfms.domain.packageIssue.usecases.ListPackageIssue
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListPackageIssueService
@Inject
constructor(
    private val listPackageIssue: ListPackageIssue,  // Domain service to fetch package issue data
    private val requestMapper: ListPackageIssueRequestMapper,
    private val responseMapper: ListPackageIssueResponseMapper
) {

    private val logger = LoggerFactory.getLogger(ListPackageIssueService::class.java)

    suspend fun invoke(page: Int, size: Int, returnRequested: Boolean? = null): ListPackageIssueResponse {
        logger.info("Received request to list package issues: page = {}, size = {}", page, size)

        // Convert the request to the domain model using the request mapper
        val domainRequest = requestMapper.toDomain(page, size)

        // Fetch package issues from the domain service
        val packageIssueResult = listPackageIssue.invoke(
            page = domainRequest.page,
            size = domainRequest.size,
            returnRequested = returnRequested
        )

        logger.info("Fetched package issue records: {}", packageIssueResult)

        // Convert the domain result to API response using the response mapper
        return responseMapper.toResponse(
            packageIssues = packageIssueResult.data,
            page = domainRequest.page,
            size = domainRequest.size,
            totalPages = calculateTotalPages(packageIssueResult.totalRecords, domainRequest.size),
            totalRecords = packageIssueResult.totalRecords
        )
    }

    private fun calculateTotalPages(totalRecords: Int, size: Int): Int {
        logger.info("Calculating total pages for totalRecords: {}, size: {}", totalRecords, size)
        return if (totalRecords == 0) 0 else (totalRecords + size - 1) / size
    }
}