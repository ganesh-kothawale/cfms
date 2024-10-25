package `in`.porter.cfms.domain.hlp.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class RecordHlpDetails
@Inject
constructor(
    private val repo: HlpsRepo,
) : Traceable {

    suspend fun invoke(hlpDetails: HlpDetailsDraft) = trace {
        repo.getByHlpOrderId(hlpDetails.hlpOrderId)
            ?.let { throw CfmsException("HLP record already exists for hlp order id: ${hlpDetails.hlpOrderId}") }
        repo.create(hlpDetails)
    }
}
