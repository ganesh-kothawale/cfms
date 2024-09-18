package `in`.porter.cfms.api.service.franchises.mappers

import `in`.porter.cfms.api.models.franchises.RecordFranchiseAddressRequest
import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.models.franchises.RecordFranchisePOCRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.Instant
import javax.inject.Inject
import `in`.porter.cfms.api.models.FranchiseStatus as ModelFranchiseStatus
import `in`.porter.cfms.domain.franchise.FranchiseStatus as DomainFranchiseStatus

class RecordFranchiseDetailsRequestMapper
@Inject
constructor(
) {
    fun toDomain(req: RecordFranchiseDetailsRequest, franchiseId: String) =
        `in`.porter.cfms.domain.usecases.entities.RecordFranchiseDetailsRequest(
            franchiseId = franchiseId,
            address = toFranchiseAddress(req.address),
            poc = toFranchisePOC(req.poc),
            customerShipmentLabelRequired = req.customerShipmentLabelRequired,
            radiusCoverage = req.radiusCoverage,
            hlpEnabled = req.hlpEnabled,
            status = mapModelToDomainStatus(req.status),
            daysOfOperation = req.daysOfOperation,
            cutOffTime = req.cutOffTime,
            startTime = req.startTime,
            endTime = req.endTime,
            porterHubName = req.porterHubName,
            franchiseGst = req.franchiseGst,
            franchisePan = req.franchisePan,
            franchiseCanceledCheque = req.franchiseCanceledCheque,
            kamUser = req.kamUser,
            showCrNumber = req.showCrNumber
        )

    private fun toFranchiseAddress(address: RecordFranchiseAddressRequest) =
        `in`.porter.cfms.domain.usecases.entities.RecordFranchiseAddressRequest(
            address = address.address,
            city = address.city,
            state = address.state,
            pincode = address.pincode,
            latitude = address.latitude,
            longitude = address.longitude
        )

    private fun toFranchisePOC(poc: RecordFranchisePOCRequest) =
        `in`.porter.cfms.domain.usecases.entities.RecordFranchisePOCRequest(
            name = poc.name,
            primaryNumber = poc.primaryNumber,
            email = poc.email
        )

    private fun LocalDateTime.toInstant(): Instant {
        return atZone(ZoneId.systemDefault()).toInstant()
    }


    private fun mapModelToDomainStatus(status: ModelFranchiseStatus): DomainFranchiseStatus =
        when (status) {
            ModelFranchiseStatus.Active -> DomainFranchiseStatus.Active
            ModelFranchiseStatus.Inactive -> DomainFranchiseStatus.Inactive
            ModelFranchiseStatus.Suspended -> DomainFranchiseStatus.Suspended
        }

    // Mapping DomainFranchiseStatus to ModelFranchiseStatus
    private fun mapDomainToModelStatus(status: DomainFranchiseStatus): ModelFranchiseStatus =
        when (status) {
            DomainFranchiseStatus.Active -> ModelFranchiseStatus.Active
            DomainFranchiseStatus.Inactive -> ModelFranchiseStatus.Inactive
            DomainFranchiseStatus.Suspended -> ModelFranchiseStatus.Suspended
        }

}

