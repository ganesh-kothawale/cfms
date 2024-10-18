package `in`.porter.cfms.api.service.courierpartner.usecases

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
                .also { println("[RecordCPConnectionService]") }
                .let { recordCPConnection.invoke(it) }
                .let { RecordCPConnectionResponse("CP connection recorded successfully.") }
        } catch (e: CfmsException) {
            throw CfmsException(e.message)
        }
    }
}
