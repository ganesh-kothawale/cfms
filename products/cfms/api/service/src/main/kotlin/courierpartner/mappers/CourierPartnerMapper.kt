package courierpartner.mappers

import `in`.porter.cfms.api.models.courierpartner.CourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerDomain

class CourierPartnerMapper {
  companion object {
    fun fromDomain(courierPartnerDomain: CourierPartnerDomain): CourierPartner {
      return CourierPartner(
        id = courierPartnerDomain.id,
        createdAt = courierPartnerDomain.createdAt,
        cpId = courierPartnerDomain.cpId,
        franchiseId = courierPartnerDomain.franchiseId,
        manifestImageUrl = courierPartnerDomain.manifestImageUrl,
        courierPartnerName = courierPartnerDomain.courierPartnerName
      )
    }
  }
}
