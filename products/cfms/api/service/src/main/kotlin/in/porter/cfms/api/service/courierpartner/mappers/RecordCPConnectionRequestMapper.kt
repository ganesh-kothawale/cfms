package `in`.porter.cfms.api.service.courierpartner.mappers

import `in`.porter.cfms.api.models.cpConnections.RecordCPConnectionApiRequest
import `in`.porter.cfms.domain.cpConnections.entities.RecordCPConnectionRequest
import javax.inject.Inject

class RecordCPConnectionRequestMapper
@Inject
constructor() {
    companion object {
        const val MANIFEST_LINK = ""
    }

    fun toDomain(req: RecordCPConnectionApiRequest): RecordCPConnectionRequest {
        validateRequest(req)
        return RecordCPConnectionRequest(
            cpId = req.cpId,
            franchiseId =   req.franchiseId,
            manifestImageLink =  req.manifestImageLink?: MANIFEST_LINK
        )
    }

    private fun validateRequest(req: RecordCPConnectionApiRequest) = req.let {
        if (it.cpId <= 0) throw IllegalArgumentException("Invalid cpId")
        if (it.franchiseId.isBlank()) throw IllegalArgumentException("franchiseId cannot be blank")
    }
}
