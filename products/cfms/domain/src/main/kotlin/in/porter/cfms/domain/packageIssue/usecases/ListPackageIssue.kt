package `in`.porter.cfms.domain.packageIssue.usecases

import `in`.porter.cfms.domain.packageIssue.entities.DomainListAllPackageIssueRequest
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssueResult
import `in`.porter.cfms.domain.packageIssue.repos.PackageIssueRepo
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListPackageIssue
@Inject
constructor(
    private val packageIssueRepo: PackageIssueRepo
) {

    private val logger = LoggerFactory.getLogger(PackageIssue::class.java)

    suspend fun invoke(request:DomainListAllPackageIssueRequest): PackageIssueResult {
        logger.info("Listing package issues with page: ${request.page}, size: ${request.size}")
        val totalRecords = packageIssueRepo.countAllPackageIssue(request)
        val packageIssues = packageIssueRepo.findAllPackageIssue(request)
        logger.info("Fetched ${packageIssues.size} package issues out of $totalRecords total records.")
        return PackageIssueResult(
            data = packageIssues,
            totalRecords = totalRecords
        )
    }
}