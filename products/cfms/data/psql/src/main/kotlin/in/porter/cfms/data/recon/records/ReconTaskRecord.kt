package `in`.porter.cfms.data.recon.records

data class ReconTaskRecord (
    val taskId: String,
    val crId: String,
    val cpName: String,
    val cpImageUrl: String?,
    val shipmentImageUrl: String?,
    val awb: String,
    val status: String
)