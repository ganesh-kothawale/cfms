package `in`.porter.cfms.api.service.franchises.usecases

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.franchises.UpdateFranchiseRequest
import `in`.porter.cfms.api.service.franchises.mappers.UpdateFranchiseDetailsRequestMapper
import `in`.porter.cfms.domain.franchise.usecases.internal.UpdateFranchiseDetails
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdateFranchiseDetailsService
@Inject
constructor(
    private val mapper: UpdateFranchiseDetailsRequestMapper,
    private val updateFranchiseDetails: UpdateFranchiseDetails
) {
    private val logger = LoggerFactory.getLogger(UpdateFranchiseDetailsService::class.java)

    suspend fun invoke(request: UpdateFranchiseRequest): Int? {
        logger.info("Invoke function called to send the data to domain for franchise ID: {}", request.franchiseId)

        // Validate franchise ID
        if (request.franchiseId.isBlank()) {
            logger.warn("Franchise ID cannot be empty.")
            throw CfmsException("Franchise ID is mandatory.")
        }

        // Validate courier partners if present
        if (request.courierPartners.isNotEmpty()) {
            logger.info("Validating courier partners.")
        } else {
            logger.warn("No courier partners provided.")
            throw CfmsException("At least one courier partner must be provided.")
        }

        try {
            val franchiseDomain = mapper.toDomain(request)
            return updateFranchiseDetails.invoke(franchiseDomain)

        } catch (e: CfmsException) {
            logger.error("Exception occurred while updating franchise: {}", e.message)
            throw CfmsException("Exception occurred while updating franchise: ${e.message}")
        } catch (e: Exception) {
            logger.error("Error occurred while updating franchise: {}", e.message)
            throw CfmsException("Error occurred while updating franchise: ${e.message}")
        }
    }
}
