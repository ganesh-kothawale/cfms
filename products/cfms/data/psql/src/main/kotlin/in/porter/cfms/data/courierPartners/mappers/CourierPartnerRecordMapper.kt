package `in`.porter.cfms.data.courierPartners.mappers

import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerRecord
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import java.time.Instant
import javax.inject.Inject

class CourierPartnerRecordMapper
  @Inject
  constructor(){

  fun toRecord(createCourierPartnerRequest: CreateCourierPartnerRequest) = CourierPartnerRecord(
    courierPartnerId = createCourierPartnerRequest.courierPartnerId,
    franchiseId = createCourierPartnerRequest.franchiseId,
    manifestImageUrl = createCourierPartnerRequest.manifestImageLink,
    createdAt =  Instant.now()
    )
  }
