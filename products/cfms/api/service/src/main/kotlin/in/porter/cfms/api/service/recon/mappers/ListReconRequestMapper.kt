package `in`.porter.cfms.api.service.recon.mappers

import `in`.porter.cfms.api.models.recon.ListReconRequest
import javax.inject.Inject

class ListReconRequestMapper
@Inject constructor() {

    fun toDomain(page: Int, size: Int): ListReconRequest {
        return ListReconRequest(
            page = page,
            size = size
        )
    }
}