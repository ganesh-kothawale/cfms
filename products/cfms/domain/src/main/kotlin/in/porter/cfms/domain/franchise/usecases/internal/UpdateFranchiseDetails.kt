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
        val franchise = franchiseRepo.getByCode(req.franchiseId)
            ?: throw CfmsException("Franchise not found")

        // Validate that at least one of the other fields is provided
        val hasMandatoryField = req.pocName.isNotBlank() ||
                req.primaryNumber.isNotBlank() ||
                req.email.isNotBlank() ||
                req.address.isNotBlank() ||
                req.latitude != BigDecimal.ZERO ||
                req.longitude != BigDecimal.ZERO ||
                req.city.isNotBlank() ||
                req.state.isNotBlank() ||
                req.pincode.isNotBlank() ||
                req.porterHubName != null ||
                req.franchiseGst != null ||
                req.franchisePan != null ||
                req.franchiseCanceledCheque != null ||
                req.teamId != null ||
                req.daysOfOperation != null ||
                req.startTime.isNotBlank() ||
                req.endTime.isNotBlank() ||
                req.cutOffTime.isNotBlank() ||
                req.kamUser != null ||
                req.radiusCoverage != BigDecimal.ZERO ||
                req.showCrNumber != null ||
                req.courierPartners.isNotEmpty()

        if (!hasMandatoryField) {
            throw CfmsException("At least one detail other than franchise ID must be provided for the update.")
        }

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
