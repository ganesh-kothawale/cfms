package `in`.porter.cfms.data.courierPartners.repos

import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerRecord
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class PsqlCourierPartnerRepo
@Inject
constructor(
  private val queries: CourierPartnerQueries,
  private val mapper: CourierPartnerRecordMapper
) : Traceable, CourierPartnerRepo {
  companion object : Logging


  override suspend fun create(createCourierPartnerRequest: CreateCourierPartnerRequest): Int = trace {
      mapper.toRecord(createCourierPartnerRequest)
        .let { queries.record(it) }
    }

//  override suspend fun getById(courierPartnerId: Int): CourierPartnerRecord? =
//    trace("getById") {
//      queries.getById(courierPartnerId)
//        ?.let { mapper.fromRecord(it) }
//    }

}