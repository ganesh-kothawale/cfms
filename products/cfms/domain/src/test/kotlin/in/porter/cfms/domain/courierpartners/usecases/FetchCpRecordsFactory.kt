package `in`.porter.cfms.domain.courierpartners.usecases

import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsRequest

object FetchCpRecordsFactory {
  fun buildFetchCpRecordsRequest(): FetchCpRecordsRequest {
    return FetchCpRecordsRequest(
      page = 1,
      pageSize = 1,
      franchiseId = 1
    )
  }
}
