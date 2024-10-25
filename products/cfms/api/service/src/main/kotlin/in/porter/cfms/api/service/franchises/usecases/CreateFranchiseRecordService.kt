package `in`.porter.cfms.api.service.franchises.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.franchises.Data
import `in`.porter.cfms.api.models.franchises.ErrorResponse
import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.models.franchises.FranchiseResponse
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
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
    private val recordFranchiseDetails: RecordFranchiseDetails,
    private val createAuditLogService: CreateAuditLogService
) : Traceable {

    suspend fun invoke(request: RecordFranchiseDetailsRequest) = trace {
        val generatedFranchiseId = CommonUtils.generateRandomAlphaNumeric(10)
        val franchise = mapper.toDomain(request, generatedFranchiseId)
        try {
            val franchiseId = recordFranchiseDetails.invoke(franchise)
            val data = Data(
                message = " Franchise created successfully",
                franchise_id = franchiseId
            )
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = generatedFranchiseId,
                    entityType = "Franchise",
                    status = "Created",
                    message = "Franchise created successfully",
                    // TODO: Replace hardcoded user ID with actual user ID when available
                    updatedBy = 123  // Hardcoded for now
                )
            )
            FranchiseResponse(data = data)
        } catch (e: Exception) {
            val errorResponse = when (e) {
                is CfmsException -> {
                    listOf(
                        ErrorResponse(
                            message = "Invalid input data",
                            details = e.message
                        )
                    )
                }
                else -> {
                    listOf(
                        ErrorResponse(
                            message = "Failed to create Franchise",
                            details = e.message ?: "An unexpected error occurred on the server."
                        )
                    )
                }
            }
            FranchiseResponse(error = errorResponse)
        }

    }
}
