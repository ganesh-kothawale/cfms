package `in`.porter.cfms.api.models.recon

import java.math.BigDecimal

data class CreateReconRequest(
    val orderId: String,
    val taskId: String,
    val teamId: String,
    val reconStatus: String,  // Pending, Completed, Return Requested
    val packagingRequired: Boolean = false,
    val prePackagingImageUrl: String?,
    val shipmentIsEnvelopeOrDocument: Boolean = false,
    val shipmentWeight: BigDecimal?,
    val weightPhotoUrl: String?,
    val shipmentDimensionsCmOrInch: String?,
    val shipmentLength: BigDecimal?,
    val shipmentWidth: BigDecimal?,
    val shipmentHeight: BigDecimal?,
    val dimensionsPhotoUrls: String?,
    val returnRequested: Boolean = false,
    val returnImageUrl: String?
)
