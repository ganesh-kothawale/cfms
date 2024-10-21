package `in`.porter.cfms.api.service.courierpartner.mappers

import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest
import `in`.porter.cfms.domain.cpConnections.entities.FetchCPConnectionsRequest
import javax.inject.Inject

class FetchCPConnectionsRequestMapper
@Inject
constructor() {

    fun toDomain(req: FetchCPConnectionsApiRequest): FetchCPConnectionsRequest {
        validateRequest(req)
        return FetchCPConnectionsRequest(
            page = req.page,
            size = req.size,
            franchiseId = req.franchiseId
        )
    }

    private fun validateRequest(req: FetchCPConnectionsApiRequest) {
        if (req.page < 1 || req.size < 1 || req.size > 100)
            throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
    }
}
