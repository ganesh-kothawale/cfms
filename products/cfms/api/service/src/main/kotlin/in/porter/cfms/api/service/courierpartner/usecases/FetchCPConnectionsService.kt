package `in`.porter.cfms.api.service.courierpartner.usecases

import `in`.porter.cfms.api.models.cpConnections.ErrorResponse
import `in`.porter.cfms.api.service.courierpartner.mappers.FetchCPConnectionsRequestMapper
import `in`.porter.cfms.api.service.courierpartner.mappers.FetchCPConnectionsResponseMapper
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiRequest
import `in`.porter.cfms.api.models.cpConnections.FetchCPConnectionsApiResponse
import `in`.porter.cfms.domain.cpConnections.usecases.internal.FetchCPConnections
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchCPConnectionsService
@Inject
constructor(
    private val requestMapper: FetchCPConnectionsRequestMapper,
    private val responseMapper: FetchCPConnectionsResponseMapper,
    private val fetchCPConnections: FetchCPConnections
) : Traceable {

    suspend fun invoke(req: FetchCPConnectionsApiRequest): FetchCPConnectionsApiResponse = trace {
        try {
            requestMapper.toDomain(req)
                .let { fetchCPConnections.invoke(it) }
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
                            message = "Failed to retrieve CP connections",
                            details = e.message ?: "An unexpected error occurred on the server."
                        )
                    )
                }
            }
            FetchCPConnectionsApiResponse.Error(errorResponse)
        }
    }
}
