package `in`.porter.cfms.domain.cpConnections.entities

data class FetchCPConnectionsRequest(
  val page: Int,
  val size: Int,
  val franchiseId: String?,
)
