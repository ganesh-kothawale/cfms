package `in`.porter.cfms.data.courierPartners.records

import java.time.Instant

data class CourierPartnerTableData(
  val id: Int,
  val createdAt: Instant,
  val courierPartnerId: Int,
  val franchiseId: Int,
  val manifestImageUrl: String?,
  )
