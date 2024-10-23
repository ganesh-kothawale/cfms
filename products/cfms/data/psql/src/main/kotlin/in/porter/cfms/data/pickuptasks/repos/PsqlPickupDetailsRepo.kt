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
                // Map the domain PickupDetails to a record
                val pickupDetailsRecord = pickupDetailsMapper.toRecord(pickupDetails)

                // Insert the record and return the generated pickupDetailsId
                queries.insert(pickupDetailsRecord)
            } catch (e: Exception) {
                logger.error("Error occurred while creating pickup details: ${e.message}", e)
                throw CfmsException("Failed to create pickup details: ${e.message}")
            }
        }
}
