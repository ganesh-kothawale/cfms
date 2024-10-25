package `in`.porter.cfms.api.service.courierpartner.mappers

import `in`.porter.cfms.api.models.cpConnections.CPConnection as CPConnectionApi
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiResponse
import `in`.porter.cfms.domain.cpConnections.entities.FetchCPConnectionsResponse
import `in`.porter.cfms.domain.exceptions.CfmsException
import javax.inject.Inject

class FetchCPConnectionsResponseMapper
@Inject
constructor(
    private val mapper: CPConnectionMapper,
) {

    fun fromDomain(res: FetchCPConnectionsResponse) = FetchCPConnectionsApiResponse.Success(
        cpConnections = mapCPConnections(res),
        page = res.page,
        size = res.size,
        totalPages = res.totalPages,
        totalRecords = res.totalRecords,
    )

    private fun mapCPConnections(res: FetchCPConnectionsResponse): List<CPConnectionApi> {
        try {
            val cps = res.cps
            return res.cpConnections
                .map { mapper.fromDomain(it, cps.find { cp -> cp.id == it.cpId }!!) }
        } catch (e: NullPointerException) {
            throw Exception("Mapping Cp connections failed due to missing courier partner")
        }
    }
}
