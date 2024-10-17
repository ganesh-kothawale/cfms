package `in`.porter.cfms.api.service.hlp.usecases

import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest
import `in`.porter.cfms.api.service.hlp.mappers.RecordHlpDetailsRequestMapper
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.hlp.usecases.RecordHlpDetails
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.cfms.api.models.exceptions.CfmsException as CfmsExceptionApi
import javax.inject.Inject

class RecordHlpDetailsService
@Inject
constructor(
    private val mapper: RecordHlpDetailsRequestMapper,
    private val recordHlpDetails: RecordHlpDetails,
) : Traceable {

    suspend fun invoke(request: RecordHlpDetailsRequest) = trace {
        try {
            mapper.toDomain(request)
                .let { recordHlpDetails.invoke(it) }
        } catch (e: CfmsException) {
            throw CfmsExceptionApi(e.message)
        }
    }
}
