package `in`.porter.cfms.api.models.cpConnections

data class FetchCPConnectionsApiRequest(
  val page: Int,
  val size: Int,
  val franchiseId: String? = null
)
