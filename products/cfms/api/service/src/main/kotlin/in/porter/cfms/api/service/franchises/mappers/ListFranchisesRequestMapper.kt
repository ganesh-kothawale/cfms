package `in`.porter.cfms.api.service.franchises.mappers

import `in`.porter.cfms.api.models.franchises.ListFranchisesRequest
import `in`.porter.cfms.domain.franchise.entities.DomainListFranchisesRequest
import javax.inject.Inject

class ListFranchisesRequestMapper
@Inject
constructor() {
    fun toDomain(request: ListFranchisesRequest): DomainListFranchisesRequest {
        return DomainListFranchisesRequest(
            page = request.page,
            size = request.size,
            createdDate = request.createdDate,
            updatedDate = request.updatedDate,
            franchiseIds = request.franchiseIds,
            pocPrimaryNumber = request.pocPrimaryNumber,
            emailId = request.emailId,
            porterHubNames = request.porterHubNames,
            status = request.status,
            kams = request.kams,
            cities = request.cities,
            states = request.states,
            pincode = request.pincode,
            geoRegionId = request.geoRegionId,
            radiusCoverage = request.radiusCoverage
        )
    }
}
