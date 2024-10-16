package `in`.porter.cfms.domain.franchise.usecases.internal

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.usecases.entities.RecordFranchiseDetailsRequest
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import java.time.Instant
import javax.inject.Inject

class CreateFranchise
@Inject constructor(
    private val repo: FranchiseRepo,
) : Traceable {

    suspend fun invoke(req: RecordFranchiseDetailsRequest): Unit {
        if (req.poc.name.isBlank()) {
            throw CfmsException("POC name cannot be empty.")
        }
        if (!isValidEmail(req.poc.email)) {
            throw CfmsException("Invalid email format.")
        }
        if (req.address.address.isBlank()) {
            throw CfmsException("Address cannot be empty.")
        }

        try {
            Franchise(
                franchiseId = req.franchiseId,
                pocName = req.poc.name,
                primaryNumber = req.poc.primaryNumber,
                email = req.poc.email,
                address = req.address.address,
                latitude = req.address.latitude,
                longitude = req.address.longitude,
                city = req.address.city,
                state = req.address.state,
                pincode = req.address.pincode,
                porterHubName = req.porterHubName,
                franchiseGst = req.franchiseGst,
                franchisePan = req.franchisePan,
                franchiseCanceledCheque = req.franchiseCanceledCheque,
                status = req.status,
                teamId = null,
                daysOfOperation = req.daysOfOperation,
                startTime = req.startTime,
                endTime = req.endTime,
                cutOffTime = req.cutOffTime,
                kamUser = req.kamUser,
                hlpEnabled = req.hlpEnabled,
                radiusCoverage = req.radiusCoverage,
                showCrNumber = req.showCrNumber,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            ).let { repo.create(it) }
        } catch (e: Exception) {
            throw CfmsException("Failed to create franchise: ${e.message}")
        }
    }

    // Helper function to validate email format
    private fun isValidEmail(email: String): Boolean {
        // Simple regex for demonstration; consider using a more robust validation
        return email.matches(Regex("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$"))
    }
}
