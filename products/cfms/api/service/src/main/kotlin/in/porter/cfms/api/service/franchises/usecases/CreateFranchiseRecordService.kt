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
    suspend fun invoke(request: RecordFranchiseDetailsRequest): FranchiseResponse = trace {
        try {
            val generatedFranchiseId = CommonUtils.generateRandomAlphaNumeric(10)
            mapper.toDomain(request, generatedFranchiseId)
                .let { recordFranchiseDetails.invoke(it) }

            // Create audit log after successful franchise creation
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
