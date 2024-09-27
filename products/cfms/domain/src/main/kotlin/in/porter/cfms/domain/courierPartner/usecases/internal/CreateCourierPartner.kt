package `in`.porter.cfms.domain.courierPartner.usecases.internal

import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class CreateCourierPartner
@Inject
constructor(
//  private val repo: CourierPartnerRepo,
  private val recordCourierPartner: RecordCourierPartner

) : Traceable {

  suspend fun invoke(req: CreateCourierPartnerRequest) {
//    repo.getById(req.courierPartnerId)
//      ?.let { throw Exception("courier partner already exists") }
    val courierPartnerId = recordCourierPartner.invoke(req)
  }

}