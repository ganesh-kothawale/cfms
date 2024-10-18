package `in`.porter.cfms.api.models.cpConnections

data class FetchCPConnectionsApiRequest(
  val page: Int,
  val pageSize: Int,
  val franchiseId: String? = null
)
