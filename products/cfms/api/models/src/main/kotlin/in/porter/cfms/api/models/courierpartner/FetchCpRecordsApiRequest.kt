package `in`.porter.cfms.api.models.courierpartner

data class FetchCpRecordsApiRequest(
  val page: Int,
  val pageSize: Int,
  val franchiseId: Int? = null
)




