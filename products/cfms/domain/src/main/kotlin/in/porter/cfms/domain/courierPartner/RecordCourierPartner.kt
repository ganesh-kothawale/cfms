package `in`.porter.cfms.domain.courierPartner

import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class RecordCourierPartner
@Inject
constructor(
  private val repo: CourierPartnerRepo,
) : Traceable {

  suspend fun invoke(req: CreateCourierPartnerRequest): Int = trace {
      repo.create(req)
    }
}