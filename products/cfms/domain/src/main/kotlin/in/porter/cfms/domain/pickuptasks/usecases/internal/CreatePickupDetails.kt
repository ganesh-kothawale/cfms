package `in`.porter.cfms.domain.pickuptasks.usecases.internal

import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails
import `in`.porter.cfms.domain.pickuptasks.repos.PickupDetailsRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CreatePickupDetails @Inject constructor(
    private val pickupDetailsRepo: PickupDetailsRepo
) {

    private val logger = LoggerFactory.getLogger(CreatePickupDetails::class.java)

    suspend fun invoke(pickupDetails: PickupDetails): String {
        logger.info("Creating new pickup details: $pickupDetails")

        // Call the repository to persist the pickup details and get the generated pickupDetailsId
        val pickupDetailsId = pickupDetailsRepo.create(pickupDetails)

        logger.info("Pickup details created with ID: $pickupDetailsId")
        return pickupDetailsId
    }
}
