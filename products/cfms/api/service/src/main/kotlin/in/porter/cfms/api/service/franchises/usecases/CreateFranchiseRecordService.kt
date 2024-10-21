package `in`.porter.cfms.api.service.franchises.usecases

import `in`.porter.cfms.api.models.franchises.Data
import `in`.porter.cfms.api.models.franchises.ErrorResponse
import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.models.franchises.FranchiseResponse
import `in`.porter.cfms.api.service.exceptions.CfmsException
import `in`.porter.cfms.api.service.franchises.mappers.RecordFranchiseDetailsRequestMapper
import `in`.porter.cfms.api.service.utils.CommonUtils
import `in`.porter.cfms.domain.franchise.usecases.internal.RecordFranchiseDetails
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class CreateFranchiseRecordService
@Inject
constructor(
    private val mapper: RecordFranchiseDetailsRequestMapper,
    private val recordFranchiseDetails: RecordFranchiseDetails
) : Traceable {
    suspend fun invoke(request: RecordFranchiseDetailsRequest): FranchiseResponse = trace {
        try {
            val generatedFranchiseId = CommonUtils.generateRandomAlphaNumeric(10)
            mapper.toDomain(request, generatedFranchiseId)
                .let { recordFranchiseDetails.invoke(it) }

            val data = Data(
                message = "Franchise created successfully",
                franchise_id = generatedFranchiseId
            )

            FranchiseResponse(data = data)
        } catch (e: CfmsException) {
            val errorResponse = when (e) {
                is CfmsException -> {
                    listOf(
                        ErrorResponse(
                            message = "Invalid input data",
                            details = e.message ?: "No additional details"
                        )
                    )
                }

                else -> {
                    listOf(
                        ErrorResponse(
                            message = "Franchise creation failed",
                            details = e.message ?: "Failed to store franchise in DB, rolling back Porter creation"
                        )
                    )
                }
            }
            FranchiseResponse(error = errorResponse)
        }
    }
}
