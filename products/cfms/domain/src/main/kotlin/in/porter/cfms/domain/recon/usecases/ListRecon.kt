package `in`.porter.cfms.domain.recon.usecases

import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.entities.ReconResult
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListRecon
@Inject
constructor(
    private val reconRepo: ReconRepo
) {

    private val logger = LoggerFactory.getLogger(Recon::class.java)

    suspend fun listRecon(page: Int, size: Int): ReconResult {
        logger.info("Listing recons with page: $page, size: $size")

        // Fetch the total number of recon records
        val totalRecords = reconRepo.countAllRecons()

        val recons = reconRepo.findAllRecons(page, size)

        // Log the result
        logger.info("Fetched ${recons.size} recons out of $totalRecords total records.")

        // Return the result with the fetched data and total record count
        return ReconResult(
            data = recons,
            totalRecords = totalRecords
        )
    }
}


