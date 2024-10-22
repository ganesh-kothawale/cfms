package `in`.porter.cfms.data.recon.records

import java.math.BigDecimal
import java.time.Instant

data class ReconRecord(
    val reconId: String,
    val orderId: String,
    val taskId: String,
    val teamId: String,
    val reconStatus: String?,
    val packagingRequired: Boolean?,
    val prePackagingImageUrl: String?,
    val shipmentIsEnvelopeOrDocument: Boolean?,
    val shipmentWeight: BigDecimal?,
    val weightPhotoUrl: String?,
    val shipmentDimensionsCmOrInch: String?,
    val shipmentLength: BigDecimal?,
    val shipmentWidth: BigDecimal?,
    val shipmentHeight: BigDecimal?,
    val dimensionsPhotoUrls: String?,
    val returnRequested: Boolean?,
    val returnImageUrl: String?,
    val createdAt: Instant,
    val updatedAt: Instant
)
