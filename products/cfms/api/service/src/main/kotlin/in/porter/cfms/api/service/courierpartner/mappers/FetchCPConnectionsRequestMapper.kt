package `in`.porter.cfms.api.service.courierpartner.mappers

import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest
import `in`.porter.cfms.domain.cpConnections.entities.FetchCPConnectionsRequest
import javax.inject.Inject

class FetchCPConnectionsRequestMapper
@Inject
constructor() {

    fun toDomain(req: FetchCPConnectionsApiRequest) = FetchCPConnectionsRequest(
        page = req.page,
        size = req.pageSize,
        franchiseId = req.franchiseId
    )
}
