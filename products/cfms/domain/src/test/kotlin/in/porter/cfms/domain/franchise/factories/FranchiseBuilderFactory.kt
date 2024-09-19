package `in`.porter.cfms.domain.franchise.factories

import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.usecases.entities.RecordFranchiseDetailsRequest
import java.time.Instant

class FranchiseBuilderFactory {

    fun buildFromRequest(request: RecordFranchiseDetailsRequest): Franchise {
        return Franchise(
            franchiseId = request.franchiseId,
            pocName = request.poc.name,
            primaryNumber = request.poc.primaryNumber,
            email = request.poc.email,
            address = request.address.address,
            latitude = request.address.latitude,
            longitude = request.address.longitude,
            city = request.address.city,
            state = request.address.state,
            pincode = request.address.pincode,
            porterHubName = request.porterHubName,
            franchiseGst = request.franchiseGst,
            franchisePan = request.franchisePan,
            franchiseCanceledCheque = request.franchiseCanceledCheque,
            status = request.status,
            teamId = null,
            daysOfOperation = request.daysOfOperation,
            startTime = request.startTime,
            endTime = request.endTime,
            cutOffTime = request.cutOffTime,
            kamUser = request.kamUser,
            hlpEnabled = request.hlpEnabled,
            radiusCoverage = request.radiusCoverage,
            showCrNumber = request.showCrNumber,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}