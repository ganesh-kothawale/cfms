package `in`.porter.cfms.domain.cpConnections.repos

import `in`.porter.cfms.domain.cpConnections.entities.CPConnection
import `in`.porter.cfms.domain.cpConnections.entities.RecordCPConnectionRequest
import `in`.porter.cfms.domain.cpConnections.entities.FetchCPConnectionsRequest

interface CPConnectionRepo {

    suspend fun create(request: RecordCPConnectionRequest)

    suspend fun getByPagination(request: FetchCPConnectionsRequest): List<CPConnection>

    suspend fun getAllCount(): Int

}
