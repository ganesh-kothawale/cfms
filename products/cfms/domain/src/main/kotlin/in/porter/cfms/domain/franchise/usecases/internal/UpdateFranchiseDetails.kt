package `in`.porter.cfms.domain.franchise.usecases.internal

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.franchise.entities.UpdateFranchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import javax.inject.Inject

class UpdateFranchiseDetails
@Inject
constructor(
    private val franchiseRepo: FranchiseRepo,
) : Traceable {

    private val logger = LoggerFactory.getLogger(UpdateFranchiseDetails::class.java)

    suspend fun invoke(req: UpdateFranchise): Int? {
        logger.info("Starting update for franchise: ${req.franchiseId}")

        // Fetch the existing franchise by ID
        franchiseRepo.getByCode(req.franchiseId)
            ?: throw CfmsException("Franchise not found")

        // Proceed with the update
        val updateResult = franchiseRepo.update(req)

        // Check if the update was successful
        if (updateResult != null) {
            if (updateResult <= 0) {
                logger.error("Failed to update franchise with ID: ${req.franchiseId}")
                throw CfmsException("Failed to update franchise. Please try again.")
            }
        }

        logger.info("Franchise with ID: ${req.franchiseId} updated successfully.")
        return updateResult
    }
}
