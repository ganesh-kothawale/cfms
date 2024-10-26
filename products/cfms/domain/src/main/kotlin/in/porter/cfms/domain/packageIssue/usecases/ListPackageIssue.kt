package `in`.porter.cfms.domain.packageIssue.usecases

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

    suspend fun invoke(page: Int, size: Int, returnRequested: Boolean? = null): PackageIssueResult {
        logger.info("Listing package issues with page: $page, size: $size")

        // Fetch the total number of package issue records, using returnRequested if applicable
        val totalRecords = packageIssueRepo.countAllPackageIssue(returnRequested)

        // Fetch package issues using renamed function
        val packageIssues = packageIssueRepo.findAllPackageIssue(page, size, returnRequested)

        // Log the result
        logger.info("Fetched ${packageIssues.size} package issues out of $totalRecords total records.")

        // Return the result with the fetched data and total record count
        return PackageIssueResult(
            data = packageIssues,
            totalRecords = totalRecords
        )
    }
}