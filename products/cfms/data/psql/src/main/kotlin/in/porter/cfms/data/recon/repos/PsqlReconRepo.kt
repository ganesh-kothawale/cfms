package `in`.porter.cfms.data.recon.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.recon.ReconQueries
import `in`.porter.cfms.data.recon.mappers.ListReconMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlReconRepo
@Inject constructor(
    private val queries: ReconQueries,
    private val listReconMapper: ListReconMapper
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
                    listReconMapper.toDomain(record)
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
}
