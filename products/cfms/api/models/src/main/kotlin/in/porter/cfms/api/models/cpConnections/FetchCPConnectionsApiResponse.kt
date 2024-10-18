package `in`.porter.cfms.api.models.cpConnections

import `in`.porter.cfms.api.models.utils.ErrorResponse

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
//data class Pagination(
//  val currentPage: Int,           // Current page number
//  val pageSize: Int,              // Page size
//  val totalRecords: Int,          // Total number of records
//  val totalPages: Int             // Total number of pages
//)
