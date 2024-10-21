package `in`.porter.cfms.domain.cpConnections.entities

import `in`.porter.cfms.domain.courierPartners.entities.CourierPartner

data class FetchCPConnectionsResponse(
  val cpConnections: List<CPConnection>,
  val cps: List<CourierPartner>,
  val page: Int,
  val size: Int,
  val totalPages: Int,
  val totalRecords: Int,
)
