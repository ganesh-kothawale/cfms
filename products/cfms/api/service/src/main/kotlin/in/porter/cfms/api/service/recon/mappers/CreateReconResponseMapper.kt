package `in`.porter.cfms.api.service.recon.mappers

import `in`.porter.cfms.api.models.recon.CreateReconResponse
import javax.inject.Inject

class CreateReconResponseMapper @Inject constructor() {

    fun toResponse(reconId: String, message: String): CreateReconResponse {
        return CreateReconResponse(
            reconId = reconId,
            message = message
        )
    }
}
