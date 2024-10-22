package `in`.porter.cfms.data.recon.mappers

import `in`.porter.cfms.data.recon.ReconTable
import `in`.porter.cfms.data.recon.records.ReconRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ReconRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(ReconRowMapper::class.java)

    fun toRecord(resultRow: ResultRow): ReconRecord {
        logger.info("Mapping result row to ReconRecord")
        return ReconRecord(
            reconId = resultRow[ReconTable.reconId],
            orderId = resultRow[ReconTable.orderId],
            taskId = resultRow[ReconTable.taskId],
            teamId = resultRow[ReconTable.teamId],
            reconStatus = resultRow[ReconTable.reconStatus],
            packagingRequired = resultRow[ReconTable.packagingRequired],
            prePackagingImageUrl = resultRow[ReconTable.prePackagingImageUrl],
            shipmentIsEnvelopeOrDocument = resultRow[ReconTable.shipmentIsEnvelopeOrDocument],
            shipmentWeight = resultRow[ReconTable.shipmentWeight],
            weightPhotoUrl = resultRow[ReconTable.weightPhotoUrl],
            shipmentDimensionsCmOrInch = resultRow[ReconTable.shipmentDimensionsCmOrInch],
            shipmentLength = resultRow[ReconTable.shipmentLength],
            shipmentWidth = resultRow[ReconTable.shipmentWidth],
            shipmentHeight = resultRow[ReconTable.shipmentHeight],
            dimensionsPhotoUrls = resultRow[ReconTable.dimensionsPhotoUrls],
            returnRequested = resultRow[ReconTable.returnRequested],
            returnImageUrl = resultRow[ReconTable.returnImageUrl],
            createdAt = resultRow[ReconTable.createdAt],
            updatedAt = resultRow[ReconTable.updatedAt]
        )
    }
}
