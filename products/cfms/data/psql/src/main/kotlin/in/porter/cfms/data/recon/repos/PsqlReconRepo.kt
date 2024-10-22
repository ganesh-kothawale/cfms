package `in`.porter.cfms.data.recon.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.recon.ReconQueries
import `in`.porter.cfms.data.recon.mappers.ReconRecordMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlReconRepo
@Inject constructor(
    private val queries: ReconQueries,
    private val reconRecordMapper: ReconRecordMapper
) : Traceable, ReconRepo {

    private val logger = LoggerFactory.getLogger(PsqlReconRepo::class.java)

    override suspend fun findAllRecons(page: Int, size: Int, packagingRequired: Boolean?): List<Recon> =
        trace("findAllRecons") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving recon records with page: $page, size: $size")
                val offset = (page - 1) * size
                val records = queries.findAll(size, offset, packagingRequired)

                logger.info("Found ${records.size} recon records")
                records.map { record: ReconRecord ->
                    logger.info("Mapping recon record: $record")
                    reconRecordMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving recon records: ${e.message}", e)
                throw CfmsException("Failed to retrieve recon records: ${e.message}")
            }
        }

    override suspend fun countAllRecons(packagingRequired: Boolean?): Int =
        trace("countAllRecons") {
            try {
                logger.info("Counting recon records")
                queries.countAll(packagingRequired)
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count recon records: ${e.message}")
            }
        }

    override suspend fun create(recon: Recon): String =
        trace("create") {
            try {
                logger.info("Creating a new recon in the database")
                reconRecordMapper.toRecord(recon)
                    .let {queries.insert(it)}
                logger.info("Recon created successfully with ID: ${recon.reconId}")
                recon.reconId
            } catch (e: Exception) {
                logger.error("Error occurred while creating recon: ${e.message}", e)
                throw CfmsException("Failed to create recon: ${e.message}")
            }
        }

    override suspend fun findReconById(reconId: String): Recon? = trace("findReconById") {
        queries.findByReconId(reconId)
        ?.let { reconRecordMapper.toDomain(it) }  // Use the mapper to convert ReconRecord to domain Recon
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
