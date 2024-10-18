package `in`.porter.cfms.data.recon

import `in`.porter.cfms.data.recon.mappers.ListReconRowMapper
import `in`.porter.cfms.data.recon.records.ReconRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ReconQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val listReconRowMapper: ListReconRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(ReconQueries::class.java)

    // Retrieve recon records with pagination
    suspend fun findAll(size: Int, offset: Int): List<ReconRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching recon records with size: $size and offset: $offset")
        ReconTable
            .selectAll()
            .limit(size, offset)
            .map { row ->
                logger.info("Mapping row: $row")
                listReconRowMapper.toRecord(row)
            }
    }

    // Count total number of recon records in the database
    suspend fun countAll(): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting all recon records")
        ReconTable
            .selectAll()
            .count()
            .toInt()
    }
}
