package `in`.porter.cfms.domain.courierpartners.usecases

import `in`.porter.cfms.domain.cpConnections.entities.FetchCPConnectionsRequest

object FetchCpRecordsFactory {
  fun buildFetchCpRecordsRequest(): FetchCPConnectionsRequest {
    return FetchCPConnectionsRequest(
      page = 1,
      size = 1,
      franchiseId = ""
    )
  }
}
