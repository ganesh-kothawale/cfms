package `in`.porter.cfms.data.recon.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.recon.ReconQueries
import `in`.porter.cfms.data.recon.mappers.ReconMapper
import `in`.porter.cfms.data.recon.mappers.ReconRowMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlReconRepo
@Inject constructor(
    private val queries: ReconQueries,
    private val reconMapper: ReconMapper
) : Traceable, ReconRepo {

    private val logger = LoggerFactory.getLogger(PsqlReconRepo::class.java)

    override suspend fun findAllRecons(page: Int, size: Int): List<Recon> =
        trace("findAllRecons") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving recon records with page: $page, size: $size")
                val offset = (page - 1) * size
                val records = queries.findAll(size, offset)

                logger.info("Found ${records.size} recon records")
                records.map { record: ReconRecord ->
                    logger.info("Mapping recon record: $record")
                    reconMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving recon records: ${e.message}", e)
                throw CfmsException("Failed to retrieve recon records: ${e.message}")
            }
        }

    override suspend fun countAllRecons(): Int =
        trace("countAllRecons") {
            try {
                logger.info("Counting recon records")
                queries.countAll()
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count recon records: ${e.message}")
            }
        }

    override suspend fun create(recon: Recon): String =
        trace("create") {
            try {
                logger.info("Creating a new recon in the database")

                // Map the domain recon to a data layer record
                val reconRecord = ReconRecord(
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

                // Insert the record into the database and return the generated recon ID
                queries.insert(reconRecord)

                logger.info("Recon created successfully with ID: ${reconRecord.reconId}")
                reconRecord.reconId

            } catch (e: Exception) {
                logger.error("Error occurred while creating recon: ${e.message}", e)
                throw CfmsException("Failed to create recon: ${e.message}")
            }
        }

    override suspend fun findReconById(reconId: String): Recon? = trace("findReconById") {
        val reconRecord = queries.findByReconId(reconId)
        reconRecord?.let { reconMapper.toDomain(it) }  // Use the mapper to convert ReconRecord to domain Recon
    }

    override suspend fun deleteReconById(reconId: String) =
        try {
            logger.info("Deleting recon with ID: $reconId")
            queries.deleteReconById(reconId)
        } catch (e: Exception) {
            logger.error("Error deleting recon: ${e.message}", e)
            throw CfmsException("Failed to delete recon with ID: $reconId")
        }
}
