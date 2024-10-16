package `in`.porter.cfms.data.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.franchise.FranchiseQueries
import `in`.porter.cfms.data.franchise.mappers.FranchiseRecordMapper
import `in`.porter.cfms.data.franchise.mappers.ListFranchisesMapper
import `in`.porter.cfms.data.franchise.records.ListFranchisesRecord
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlFranchisesRepo
@Inject constructor(
    private val queries: FranchiseQueries,
    private val mapper: FranchiseRecordMapper,
    private val listMapper : ListFranchisesMapper
) : Traceable, FranchiseRepo {

    private val logger = LoggerFactory.getLogger(PsqlFranchisesRepo::class.java)

    override suspend fun create(franchiseRequest: Franchise): Unit =
        trace("create") {
            try {
                mapper.toRecord(franchiseRequest)
                    .let { queries.save(it) }
            } catch (e: CfmsException) {
                throw CfmsException("Failed to create franchise: ${e.message}")
            }
        }

    override suspend fun getByCode(franchiseCode: String): Franchise? =
        trace("getByCode") {
            queries.getByCode(franchiseCode)
                ?.let { mapper.fromRecord(it) }
        }

    override suspend fun getByEmail(email: String): Franchise? =
        trace("getByEmail") {
            queries.getByEmail(email)
                ?.let { mapper.fromRecord(it) }
        }

    override suspend fun findAll(page: Int, size: Int): List<ListFranchise> =
        trace("findAll") { _: io.opentracing.Span ->
            try {
                logger.info("Retrieving franchises with page: $page, size: $size")
                // Calculate the offset based on the page and size
                val offset = (page - 1) * size
                // Perform the query to get the list of records
                val records = queries.findAll(size, offset)

                logger.info("Retrieved ${records.size} franchises")
                logger.info("Mapping records to domain objects")

                // Map each ListFranchisesRecord to a ListFranchise
                records.map { record: ListFranchisesRecord ->
                    logger.info("Mapping record: $record")
                    // Use the ListFranchisesMapper to map the record to a ListFranchise
                    listMapper.toDomain(record)
                }
            } catch (e: Exception) {
                logger.error("Error occurred: ${e.message}", e)
                throw CfmsException("Failed to retrieve franchises: ${e.message}")
            }
        }

    override suspend fun countAll(): Int =
        trace("countAll") {
            try {
                logger.info("Counting all franchises")
                queries.countAll()
            } catch (e: CfmsException) {
                throw CfmsException("Failed to count franchises: ${e.message}")
            }
        }
}
