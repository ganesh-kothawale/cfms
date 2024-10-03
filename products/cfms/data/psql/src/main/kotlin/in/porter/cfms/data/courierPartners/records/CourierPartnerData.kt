package `in`.porter.cfms.data.courierPartners.records

import java.time.Instant

data class CourierPartnerData (
  val createdAt: Instant,
  val courierPartnerId: Int,
  val franchiseId: Int,
  val manifestImageUrl: String?,
)
