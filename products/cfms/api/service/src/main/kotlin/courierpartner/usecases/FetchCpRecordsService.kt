package courierpartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.mappers.FetchCpRecordsMapper
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerResponse
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiRequest
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiResponse
import `in`.porter.cfms.domain.courierPartner.usecases.internal.FetchCpRecords
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchCpRecordsService
@Inject
constructor(
  private val mapper: FetchCpRecordsMapper,
  private val fetchCpRecords: FetchCpRecords
) : Traceable {

  suspend fun invoke(req: FetchCpRecordsApiRequest): FetchCpRecordsApiResponse = trace {
    try {
      mapper.toDomain(req)
        .let { fetchCpRecords.invoke(it) }
        .let { mapper.fromDomain(it) }
    } catch (e: CfmsException) {
      throw CfmsException(e.message)
    }
  }
}