package `in`.porter.cfms.api.service.packageIssue.mappers

import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueRequest
import javax.inject.Inject

class ListPackageIssueRequestMapper
@Inject constructor() {

    fun toDomain(page: Int, size: Int): ListPackageIssueRequest {
        return ListPackageIssueRequest(
            page = page,
            size = size
        )
    }
}
