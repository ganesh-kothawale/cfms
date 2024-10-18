package `in`.porter.cfms.api.service.hlp.usecases

import `in`.porter.cfms.api.models.hlp.*
import `in`.porter.cfms.api.service.hlp.mappers.UpdateHlpDetailsRequestMapper
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.hlp.usecases.UpdateHlpDetails
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class UpdateHlpDetailsService
@Inject
constructor(
    private val mapper: UpdateHlpDetailsRequestMapper,
    private val updateHlpDetails: UpdateHlpDetails
) : Traceable {

    suspend fun invoke(request: UpdateHlpDetailsRequest) = trace {
        try {
            mapper.toDomain(request)
                .let { updateHlpDetails.invoke(it) }

            val data = Data(
                message = "HLP record updated successfully",
                hlpOrderId = request.hlpOrderId
            )

            RecordHlpDetailsResponse(data = data)
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
                            message = "Failed to update HLP record",
                            details = e.message ?: "An unexpected error occurred on the server."
                        )
                    )
                }
            }
            RecordHlpDetailsResponse(error = errorResponse)
        }
    }
}
