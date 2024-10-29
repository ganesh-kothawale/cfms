package `in`.porter.cfms.api.service.recon.mappers

import `in`.porter.cfms.api.models.recon.FetchReconResponse
import `in`.porter.cfms.api.models.recon.ReconTaskResponse
import `in`.porter.cfms.domain.recon.entities.ReconTask
import javax.inject.Inject

class FetchReconTasksResponseMapper @Inject constructor() {

    fun toResponse(
        recons: List<ReconTask>,
        page: Int,
        size: Int,
        totalRecords: Int,
        totalPages: Int
    ): FetchReconResponse {
        return FetchReconResponse(
            recons = recons.map { toReconTaskResponse(it) },  // Map each ReconTask to ReconTaskResponse
            page = page,
            size = size,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }

    private fun toReconTaskResponse(reconTask: ReconTask): ReconTaskResponse {
        return ReconTaskResponse(
            task_id = reconTask.taskId,
            cp_name = reconTask.cpName,
            cp_image_url = reconTask.cpImageUrl,
            shipment_image_url = reconTask.shipmentImageUrl,
            awb = reconTask.awb,
            cr_number = reconTask.crNumber,
            action = reconTask.action
        )
    }
}