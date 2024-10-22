package `in`.porter.cfms.data.recon.mappers

import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.tasks.entities.Tasks
import org.slf4j.LoggerFactory
import java.time.Instant
import javax.inject.Inject

class ReconRecordMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(ReconRecordMapper::class.java)

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

    fun toRecord(recon: Recon): ReconRecord {
        return ReconRecord(
            reconId = recon.reconId,
            orderId = recon.orderId,
            taskId = recon.taskId,
            teamId = recon.teamId,
            reconStatus = recon.reconStatus!!,  // Assuming non-null status
            packagingRequired = recon.packagingRequired!!,
            prePackagingImageUrl = recon.prePackagingImageUrl,
            shipmentIsEnvelopeOrDocument = recon.shipmentIsEnvelopeOrDocument!!,
            shipmentWeight = recon.shipmentWeight,
            weightPhotoUrl = recon.weightPhotoUrl,
            shipmentDimensionsCmOrInch = recon.shipmentDimensionsCmOrInch,
            shipmentLength = recon.shipmentLength,
            shipmentWidth = recon.shipmentWidth,
            shipmentHeight = recon.shipmentHeight,
            dimensionsPhotoUrls = recon.dimensionsPhotoUrls,
            returnRequested = recon.returnRequested!!,
            returnImageUrl = recon.returnImageUrl,
            createdAt = recon.createdAt,
            updatedAt = recon.updatedAt
        )
    }
}
