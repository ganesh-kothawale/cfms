package `in`.porter.cfms.api.models.recon

data class ReconTaskResponse(
    val task_id: String,
    val cr_id: String,
    val cp_name: String,
    val cp_image_url: String?,
    val shipment_image_url: String?,
    val awb: String,
    val status: String
)
