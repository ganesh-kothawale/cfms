package `in`.porter.cfms.api.service.hlp.usecases

import `in`.porter.cfms.api.models.hlp.ErrorResponse
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsRequest
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsResponse
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsResponse
import `in`.porter.cfms.api.service.hlp.mappers.FetchHlpRecordsRequestMapper
import `in`.porter.cfms.api.service.hlp.mappers.FetchHlpRecordsResponseMapper
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.api.models.exceptions.CfmsException as CfmsExceptionApi
import `in`.porter.cfms.domain.hlp.usecases.FetchHlpRecords
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchHlpRecordsService
@Inject
constructor(
    private val requestMapper: FetchHlpRecordsRequestMapper,
    private val fetchHlpRecords: FetchHlpRecords,
    private val responseMapper: FetchHlpRecordsResponseMapper
) : Traceable {

    suspend fun invoke(req: FetchHlpRecordsRequest) = trace {
        try {
            requestMapper.toDomain(req)
                .let { fetchHlpRecords.invoke(it) }
                .let { responseMapper.fromDomain(it) }
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
                            message = "Failed to retrieve HLP records",
                            details = e.message ?: "An unexpected error occurred on the server."
                        )
                    )
                }
            }
            FetchHlpRecordsResponse.Error(errorResponse)
        }
    }
}
