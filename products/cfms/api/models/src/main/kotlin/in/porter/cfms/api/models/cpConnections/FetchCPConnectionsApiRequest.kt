package `in`.porter.cfms.api.models.cpConnections

import java.time.LocalDate

data class FetchCPConnectionsApiRequest(
  val page: Int,
  val size: Int,
  val createdDate: LocalDate? = null,
  val updatedDate: LocalDate? = null,
  val courierPartners: List<String>? = null,
  val franchiseIds: List<String>? = null
)
