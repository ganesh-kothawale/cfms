package `in`.porter.cfms.domain.hlp.usecases

import `in`.porter.cfms.domain.hlp.entities.UpdateHlpDetailsRequest
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.instrumentation.opentracing.trace
import javax.inject.Inject

class UpdateHlpDetails
@Inject
constructor(
    private val repo: HlpsRepo,
) : Traceable {

    suspend fun invoke(req: UpdateHlpDetailsRequest) = trace {
        repo.update(req)
    }
}
