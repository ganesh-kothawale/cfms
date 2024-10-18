package `in`.porter.cfms.api.service.recon.mappers

import `in`.porter.cfms.api.models.recon.CreateReconRequest
import `in`.porter.cfms.domain.recon.entities.Recon
import javax.inject.Inject
import java.time.Instant

class CreateReconRequestMapper @Inject constructor() {

    fun toDomain(request: CreateReconRequest): Recon {
        return Recon(
            reconId = "",  // Will be generated in the domain layer
            orderId = request.orderId,
            taskId = request.taskId,
            teamId = request.teamId,
            reconStatus = request.reconStatus,
            packagingRequired = request.packagingRequired,
            prePackagingImageUrl = request.prePackagingImageUrl,
            shipmentIsEnvelopeOrDocument = request.shipmentIsEnvelopeOrDocument,
            shipmentWeight = request.shipmentWeight,
            weightPhotoUrl = request.weightPhotoUrl,
            shipmentDimensionsCmOrInch = request.shipmentDimensionsCmOrInch,
            shipmentLength = request.shipmentLength,
            shipmentWidth = request.shipmentWidth,
            shipmentHeight = request.shipmentHeight,
            dimensionsPhotoUrls = request.dimensionsPhotoUrls,
            returnRequested = request.returnRequested,
            returnImageUrl = request.returnImageUrl,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
