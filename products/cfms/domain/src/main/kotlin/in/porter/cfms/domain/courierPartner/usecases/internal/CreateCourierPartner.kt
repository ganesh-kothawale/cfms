package `in`.porter.cfms.domain.courierPartner.usecases.internal

import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class CreateCourierPartner
@Inject
constructor(
  private val recordCourierPartner: RecordCourierPartner
) : Traceable {

  suspend fun invoke(req: CreateCourierPartnerRequest) : Int = trace {
    recordCourierPartner.invoke(req)
      .let {it}
  }

}