package `in`.porter.cfms.domain.courierPartner.entities

data class FetchCpRecordsRequest(
  val page: Int,
  val pageSize: Int,
  val franchiseId: Int?,
)
