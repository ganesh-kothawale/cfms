package `in`.porter.cfms.data.courierPartners.repos

import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.domain.courierPartners.entities.CourierPartner
import `in`.porter.cfms.domain.courierPartners.repos.CourierPartnersRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class PsqlCourierPartnerRepo
@Inject
constructor(
    private val queries: CourierPartnerQueries,
    private val mapper: CourierPartnerRecordMapper,
) : Traceable, CourierPartnersRepo {

    override suspend fun getById(id: Int): CourierPartner? =
        trace("getById") {
            queries.getById(id)
                ?.let { mapper.fromRecord(it) }
        }

    override suspend fun getByIds(ids: List<Int>): List<CourierPartner> =
        trace("getByIds") {
            queries.getByIds(ids)
                .map { mapper.fromRecord(it) }
        }
}
