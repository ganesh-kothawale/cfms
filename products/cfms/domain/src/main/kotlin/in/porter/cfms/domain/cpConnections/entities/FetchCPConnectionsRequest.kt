package `in`.porter.cfms.domain.cpConnections.entities

import java.time.LocalDate

data class FetchCPConnectionsRequest(
  val page: Int,
  val size: Int,
  val createdDate: LocalDate? = null,
  val updatedDate: LocalDate? = null,
  val courierPartners: List<String>? = null,
  val franchiseIds: List<String>? = null
)
