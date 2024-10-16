package `in`.porter.cfms.api.service.franchises.mappers

import `in`.porter.cfms.api.models.FranchiseStatus as FranchiseStatusApi
import `in`.porter.cfms.api.models.franchises.UpdateFranchiseRequest
import `in`.porter.cfms.api.service.exceptions.CfmsException
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import `in`.porter.cfms.domain.franchise.entities.UpdateFranchise
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.Instant
import javax.inject.Inject

class UpdateFranchiseDetailsRequestMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(UpdateFranchiseDetailsRequestMapper::class.java)

    fun toDomain(request: UpdateFranchiseRequest): UpdateFranchise {
        logger.info("Mapping UpdateFranchiseRequest to UpdateFranchise")


        val poc = request.poc
        if (poc.name.isBlank() || poc.primaryNumber.isBlank() || poc.email.isBlank()) {
            throw CfmsException("POC information is incomplete.")
        }


        // Construct UpdateFranchise object
        return UpdateFranchise(
            franchiseId = request.franchiseId,
            latitude = request.address.latitude,
            longitude = request.address.longitude,
            address = request.address.address,
            city = request.address.city,
            state = request.address.state,
            pincode = request.address.pincode,
            pocName = poc.name,
            primaryNumber = poc.primaryNumber,
            email = poc.email,
            radiusCoverage = request.radiusCovered,
            hlpEnabled = request.hlpEnabled,
            isActive = request.isActive,
            daysOfTheWeek = request.daysOfTheWeek,
            cutOffTime = request.cutOffTime,
            startTime = request.startTime,
            endTime = request.endTime,
            porterHubName = request.porterHubName,
            franchiseGst = request.franchiseGst,
            franchisePan = request.franchisePan,
            franchiseCanceledCheque = request.franchiseCanceledCheque,
            courierPartners = request.courierPartners,
            kamUser = request.kamUser,
            updatedAt = Instant.now(),
            createdAt = Instant.now(),
            daysOfOperation = request.daysOfOperation,
            showCrNumber = request.showCrNumber,
            teamId = request.teamId,
            status = mapModelToDomainStatus(request.status)
        )
    }

    private fun isValidTimeFormat(time: String): Boolean {
        val timePattern = """(2[0-3]|[01]?[0-9]):([0-5]?[0-9])(:([0-5]?[0-9]))?""".toRegex()
        return timePattern.matches(time)
    }

    private fun mapModelToDomainStatus(status: FranchiseStatusApi): FranchiseStatus =
        when (status) {
            FranchiseStatusApi.Active -> FranchiseStatus.Active
            FranchiseStatusApi.Inactive -> FranchiseStatus.Inactive
            FranchiseStatusApi.Suspended -> FranchiseStatus.Suspended
        }
}
