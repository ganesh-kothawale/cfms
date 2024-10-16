package `in`.porter.cfms.api.service.franchises.mappers

import `in`.porter.cfms.api.models.franchises.ListFranchisesRequest
import javax.inject.Inject

class ListFranchisesRequestMapper
@Inject
constructor() {
    fun toDomain(
        page: Int,
        size: Int
    ): ListFranchisesRequest {
        return ListFranchisesRequest(
            page = page,
            size = size
        )
    }
}

