package `in`.porter.cfms.api.service.packageIssue.mappers

import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueRequest
import `in`.porter.cfms.domain.packageIssue.entities.DomainListAllPackageIssueRequest
import javax.inject.Inject

class ListPackageIssueRequestMapper
@Inject constructor() {

    fun toDomain(request: ListPackageIssueRequest): DomainListAllPackageIssueRequest {
        return DomainListAllPackageIssueRequest(
            page = request.page,
            size = request.size,
            returnRequested = request.returnRequested,
            orderId = request.orderId,
            awbNo = request.awbNo,
            franchiseId = request.franchiseId,
            action = request.action,
            createdDate = request.createdDate,
            updatedDate = request.updatedDate
        )
    }
}
