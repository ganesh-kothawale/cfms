package `in`.porter.cfms.data.recon.records

data class ReconTaskRecord (
    val taskId: String,
    val cpName: String,
    val cpImageUrl: String?,
    val shipmentImageUrl: String?,
    val awb: String,
    val crNumber: String?,
    val action: String?
)