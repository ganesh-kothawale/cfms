package `in`.porter.cfms.api.models.courierpartner

data class FetchCpRecordsApiRequest(
  val page: Int,
  val page_size: Int,
  val franchise_id: Int? = null
)




