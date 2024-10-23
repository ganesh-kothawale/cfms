package `in`.porter.cfms.data.pickuptasks.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.pickuptasks.PickupDetailsQueries
import `in`.porter.cfms.data.pickuptasks.mappers.PickupDetailsMapper
import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails
import `in`.porter.cfms.domain.pickuptasks.repos.PickupDetailsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PsqlPickupDetailsRepo
@Inject constructor(
    private val queries: PickupDetailsQueries,
    private val pickupDetailsMapper: PickupDetailsMapper
) : Traceable, PickupDetailsRepo {

    private val logger = LoggerFactory.getLogger(PsqlPickupDetailsRepo::class.java)

    override suspend fun create(pickupDetails: PickupDetails): String =
        trace("createPickupDetails") {
            try {
                logger.info("Creating a new pickup detail in the database")
                val pickupDetailsRecord = pickupDetailsMapper.toRecord(pickupDetails)
                queries.insert(pickupDetailsRecord)
            } catch (e: Exception) {
                logger.error("Error occurred while creating pickup details: ${e.message}", e)
                throw CfmsException("Failed to create pickup details: ${e.message}")
            }
        }

    override suspend fun findTaskById(taskId: String): PickupDetails? = trace("findTaskById") {
        queries.findByTaskId(taskId)
            ?.let { pickupDetailsMapper.toDomain(it) }
    }

    override suspend fun deleteTaskById(taskId: String) =
        try {
            logger.info("Deleting Pickup Details with ID: $taskId")
            queries.deleteTaskById(taskId)
        } catch (e: Exception) {
            logger.error("Error deleting Pickup Details: ${e.message}", e)
            throw CfmsException("Failed to delete Pickup Details with ID: $taskId")
        }
}
