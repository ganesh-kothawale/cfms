package `in`.porter.cfms.domain.cpConnections.usecases.internal

import `in`.porter.cfms.domain.cpConnections.entities.RecordCPConnectionRequest
import `in`.porter.cfms.domain.cpConnections.repos.CPConnectionRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class RecordCPConnection
@Inject
constructor(
    private val repo: CPConnectionRepo,
) : Traceable {

    suspend fun invoke(req: RecordCPConnectionRequest) = trace {
        repo.create(req)
    }
}
