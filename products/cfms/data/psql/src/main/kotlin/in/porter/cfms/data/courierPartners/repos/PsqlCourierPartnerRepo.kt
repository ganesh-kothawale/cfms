package `in`.porter.cfms.data.courierPartners.repos

import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.domain.courierPartner.entities.*
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

  override suspend fun fetchCpRecords(request: FetchCpRecordsRequest): List<CourierPartnerDomain> {
    return queries.fetchCp(request.pageSize, request.pageSize * (request.page - 1),request.franchiseId)
      .let  {it.map { mapper.toDomain(it) }}
  }
  override suspend fun getCpCount(): Int {
    return queries.getCpCount()
  }
}