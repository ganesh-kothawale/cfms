package `in`.porter.cfms.api.service.courierpartner.usecases

import `in`.porter.cfms.api.models.cpConnections.Data
import `in`.porter.cfms.api.models.cpConnections.ErrorResponse
import `in`.porter.cfms.api.service.courierpartner.mappers.RecordCPConnectionRequestMapper
import `in`.porter.cfms.api.models.cpConnections.RecordCPConnectionApiRequest
import `in`.porter.cfms.api.models.cpConnections.RecordCPConnectionResponse
import `in`.porter.cfms.domain.cpConnections.usecases.internal.RecordCPConnection
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable

import javax.inject.Inject

class RecordCPConnectionService
@Inject
constructor(
    private val mapper: RecordCPConnectionRequestMapper,
    private val recordCPConnection: RecordCPConnection
) : Traceable {

    suspend fun invoke(req: RecordCPConnectionApiRequest): RecordCPConnectionResponse = trace {
        try {
            mapper.toDomain(req)
                .let { recordCPConnection.invoke(it) }
                .let { RecordCPConnectionResponse(Data("CP connection recorded successfully.")) }
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
                            message = "Failed to record CP connection",
                            details = e.message ?: "An unexpected error occurred on the server."
                        )
                    )
                }
            }
            RecordCPConnectionResponse(error = errorResponse)
        }
    }
}
