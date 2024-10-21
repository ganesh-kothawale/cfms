package `in`.porter.cfms.api.models.cpConnections

sealed class FetchCPConnectionsApiResponse {

  data class Success(
    val cpConnections: List<CPConnection>,
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalRecords: Int
  ) : FetchCPConnectionsApiResponse()

  data class Error(
    val error: List<ErrorResponse>
  ) : FetchCPConnectionsApiResponse()

}
