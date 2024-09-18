package `in`.porter.cfms.api.service.franchises.usecases

import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsResponse
import `in`.porter.cfms.api.service.exceptions.CfmsException
import `in`.porter.cfms.api.service.franchises.mappers.RecordFranchiseDetailsRequestMapper
import `in`.porter.cfms.api.service.utils.CommonUtils
import `in`.porter.cfms.domain.franchise.usecases.internal.CreateFranchise
import `in`.porter.cfms.domain.usecases.external.RecordFranchiseDetails
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject


class CreateFranchiseRecordService
@Inject
constructor(
    private val mapper: RecordFranchiseDetailsRequestMapper,
    private val recordFranchiseDetails: RecordFranchiseDetails

) : Traceable {
    suspend fun invoke(request: RecordFranchiseDetailsRequest) = trace {
        try {
            val generatedFranchiseId = CommonUtils.generateRandomAlphaNumeric(12)
            mapper.toDomain(request, generatedFranchiseId)
                .let { recordFranchiseDetails.invoke(it) }
                .let { RecordFranchiseDetailsResponse("Franchise Created Successfully") }
        } catch (e: CfmsException) {
            throw CfmsException(e.message)
        }
    }
}
