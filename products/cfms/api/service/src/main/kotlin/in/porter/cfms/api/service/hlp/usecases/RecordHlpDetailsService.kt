package `in`.porter.cfms.api.service.hlp.usecases

import `in`.porter.cfms.api.models.hlp.Data
import `in`.porter.cfms.api.models.hlp.ErrorResponse
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsResponse
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

            val data = Data(
                message = "HLP record created successfully",
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
                            message = "Failed to create HLP record",
                            details = e.message ?: "An unexpected error occurred on the server."
                        )
                    )
                }
            }
            RecordHlpDetailsResponse(error = errorResponse)
        }
    }
}
