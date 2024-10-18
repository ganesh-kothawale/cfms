package `in`.porter.cfms.data.recon.mappers

import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.domain.recon.entities.Recon
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ReconMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(ReconMapper::class.java)

    fun toDomain(record: ReconRecord): Recon {
        logger.info("Mapping record to domain: $record")
        return Recon(
            reconId = record.reconId,
            orderId = record.orderId,
            taskId = record.taskId,
            teamId = record.teamId,
            reconStatus = record.reconStatus,
            packagingRequired = record.packagingRequired,
            prePackagingImageUrl = record.prePackagingImageUrl,
            shipmentIsEnvelopeOrDocument = record.shipmentIsEnvelopeOrDocument,
            shipmentWeight = record.shipmentWeight,
            weightPhotoUrl = record.weightPhotoUrl,
            shipmentDimensionsCmOrInch = record.shipmentDimensionsCmOrInch,
            shipmentLength = record.shipmentLength,
            shipmentWidth = record.shipmentWidth,
            shipmentHeight = record.shipmentHeight,
            dimensionsPhotoUrls = record.dimensionsPhotoUrls,
            returnRequested = record.returnRequested,
            returnImageUrl = record.returnImageUrl,
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }
}
