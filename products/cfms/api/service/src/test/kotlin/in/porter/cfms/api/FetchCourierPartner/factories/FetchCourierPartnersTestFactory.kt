package `in`.porter.cfms.api.FetchCourierPartner.factories

import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest

class FetchCourierPartnersTestFactory {
    fun buildFetchCourierPartnersRequest(
        page: Int = 1,
        pageSize: Int = 2,
        franchiseId: String = ""
    ) = FetchCPConnectionsApiRequest(
        page = page,
        size = pageSize,
        franchiseId = franchiseId
    )
}
