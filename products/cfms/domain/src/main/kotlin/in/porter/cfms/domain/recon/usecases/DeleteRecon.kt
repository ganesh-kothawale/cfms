package `in`.porter.cfms.domain.recon.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteRecon @Inject constructor(
    private val reconRepo: ReconRepo
) {

    private val logger = LoggerFactory.getLogger(DeleteRecon::class.java)

    suspend fun delete(reconId: String) {
        logger.info("Attempting to delete recon with ID: $reconId")

        // Find the recon by its ID
        reconRepo.findReconById(reconId)
            ?: throw CfmsException("Recon with ID $reconId not found")

        // Delete the recon using the repository
        reconRepo.deleteReconById(reconId)

        logger.info("Recon with ID $reconId successfully deleted")
    }
}
