package `in`.porter.cfms.api.service.recon.mappers

import `in`.porter.cfms.api.models.recon.ListReconResponse
import `in`.porter.cfms.api.models.recon.ReconResponse
import `in`.porter.cfms.domain.recon.entities.Recon

import javax.inject.Inject

class ListReconResponseMapper @Inject constructor() {

    fun toResponse(
        recons: List<Recon>,  // List of Recon (domain entity)
        page: Int,
        size: Int,
        totalPages: Int,
        totalRecords: Int
    ): ListReconResponse {
        return ListReconResponse(
            recons = recons.map { toReconResponse(it) },  // Map Recon to ReconResponse
            page = page,
            size = size,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }

    private fun toReconResponse(recon: Recon): ReconResponse {
        return ReconResponse(
            reconId = recon.reconId,
            orderId = recon.orderId,
            taskId = recon.taskId,
            teamId = recon.teamId,
            reconStatus = recon.reconStatus,
            packagingRequired = recon.packagingRequired,
            prePackagingImageUrl = recon.prePackagingImageUrl,
            shipmentIsEnvelopeOrDocument = recon.shipmentIsEnvelopeOrDocument,
            shipmentWeight = recon.shipmentWeight,
            weightPhotoUrl = recon.weightPhotoUrl,
            shipmentDimensionsCmOrInch = recon.shipmentDimensionsCmOrInch,
            shipmentLength = recon.shipmentLength,
            shipmentWidth = recon.shipmentWidth,
            shipmentHeight = recon.shipmentHeight,
            dimensionsPhotoUrls = recon.dimensionsPhotoUrls,
            returnRequested = recon.returnRequested,
            returnImageUrl = recon.returnImageUrl,
            createdAt = recon.createdAt,
            updatedAt = recon.updatedAt
        )
    }
}
