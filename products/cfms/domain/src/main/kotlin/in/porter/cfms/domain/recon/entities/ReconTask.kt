package `in`.porter.cfms.domain.recon.entities

data class ReconTask(
    val taskId: String,
    val crId: String,
    val cpName: String,
    val cpImageUrl: String?,
    val shipmentImageUrl: String?,
    val awb: String,
    val status: String
)
