package `in`.porter.cfms.data.recon.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.recon.ReconQueries
import `in`.porter.cfms.data.packageIssue.mappers.PackageIssueRecordMapper
import `in`.porter.cfms.data.recon.mappers.ReconRecordMapper
import `in`.porter.cfms.data.recon.mappers.ReconTaskRecordMapper
import `in`.porter.cfms.data.packageIssue.records.PackageIssueRecord
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.cfms.data.recon.records.ReconTaskRecord
import `in`.porter.cfms.domain.packageIssue.entities.DomainListAllPackageIssueRequest
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue
import `in`.porter.cfms.domain.packageIssue.repos.PackageIssueRepo
import `in`.porter.cfms.domain.recon.entities.Recon
import `in`.porter.cfms.domain.recon.entities.ReconTask
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlReconRepo
@Inject constructor(
    private val queries: ReconQueries,
    private val reconRecordMapper: ReconRecordMapper,
    private val reconTaskRecordMapper: ReconTaskRecordMapper,
    private val packageIssueRecordMapper: PackageIssueRecordMapper
) : Traceable, ReconRepo, PackageIssueRepo {

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
                    reconRecordMapper.toDomain(record)
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
                reconRecordMapper.toRecord(recon)
                    .let { queries.insert(it) }
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

    override suspend fun findAllReconTasks(page: Int, size: Int): List<ReconTask> =
        trace("findAllReconTasks") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving recon records with page: $page, size: $size")
                val offset = (page - 1) * size
                val records = queries.fetchReconTasks(size, offset)

                logger.info("Found ${records.size} recon records")
                records.map { record: ReconTaskRecord ->
                    logger.info("Mapping recon record: $record")
                    reconTaskRecordMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred while retrieving recon records: ${e.message}", e)
                throw CfmsException("Failed to retrieve recon records: ${e.message}")
            }
        }

    override suspend fun countAllPackageIssue(request: DomainListAllPackageIssueRequest): Int =
        trace("countAllPackageIssue") {
            try {
                logger.info("Counting package issue records")
                queries.countAllPackageIssue(request)
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count package issue records: ${e.message}")
            }
        }


    override suspend fun findAllPackageIssue(request: DomainListAllPackageIssueRequest): List<PackageIssue> = trace("findAllPackageIssue") { _: io.opentracing.Span ->
        try {
            logger.info("Retrieving package issues with page: $request.page, size: $request.size")
            val offset = (request.page - 1) * request.size
            val records = queries.findAllPackageIssue(request, offset)
            records.map { record: PackageIssueRecord ->
                logger.info("Mapping package issue record: $record")
                packageIssueRecordMapper.toDomain(record)
            }

        } catch (e: Exception) {
            logger.error("Error occurred while retrieving package issues: ${e.message}", e)
            throw CfmsException("Failed to retrieve package issues: ${e.message}")
        }
    }
}
