package `in`.porter.cfms.domain.courierpartners.usecases

import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest

object RecordCourierPartnerFactory {
  fun buildRecordCourierPartnerRequest(): CreateCourierPartnerRequest{
    return CreateCourierPartnerRequest(
      courierPartnerId = 1,
      franchiseId = 1,
      manifestImageLink = ""
    )
  }
}