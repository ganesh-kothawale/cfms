package `in`.porter.cfms.domain.recon.usecases

import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.entities.ReconResult
import `in`.porter.cfms.domain.recon.entities.ReconTaskResult
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class FetchReconTask@Inject
constructor(
    private val reconRepo: ReconRepo
) {

    private val logger = LoggerFactory.getLogger(FetchReconTask::class.java)

    suspend fun invoke(page: Int, size: Int): ReconTaskResult {
        logger.info("Listing recons with page: $page, size: $size")

        val recons = reconRepo.findAllReconTasks(page, size)
        logger.info("Fetched ${recons.size} recons out of ${recons.size} total records.")

        return ReconTaskResult(
            data = recons,
            totalRecords = recons.size
        )
    }
}