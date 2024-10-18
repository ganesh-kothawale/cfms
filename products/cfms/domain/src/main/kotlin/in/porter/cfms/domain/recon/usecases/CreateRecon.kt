package `in`.porter.cfms.domain.recon.usecases

import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreateRecon @Inject constructor(
    private val reconRepo: ReconRepo
) {

    private val logger = LoggerFactory.getLogger(CreateRecon::class.java)

    suspend fun create(recon: Recon): String {
        logger.info("Creating a new recon: $recon")

        // Call the repository to persist the recon and get the generated ID
        val reconId = reconRepo.create(recon)

        logger.info("Task created with ID: $reconId")
        return reconId
    }
}
