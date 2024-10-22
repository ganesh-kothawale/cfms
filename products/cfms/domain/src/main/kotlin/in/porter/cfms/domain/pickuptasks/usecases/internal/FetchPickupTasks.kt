package `in`.porter.cfms.domain.pickuptasks.usecases.internal

import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import `in`.porter.cfms.domain.pickuptasks.PickupTasksRepo
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTaskResult
import org.slf4j.LoggerFactory
import javax.inject.Inject

class FetchPickupTasks
@Inject
constructor(
    private val pickupTasksRepo: PickupTasksRepo,
    private val orderRepo: OrderDetailsRepo,
    private val hlpRepo: HlpsRepo
) {
    private val logger = LoggerFactory.getLogger(FetchPickupTasks::class.java)
    suspend fun invoke(page: Int, size: Int): PickupTaskResult {
        logger.info("Listing pickupTasks with page: $page, size: $size")

        val pickupTasks =  pickupTasksRepo.findAllPickupTasks(page, size)

        logger.info("Fetched ${pickupTasks.size} tasks out of total records.")

        return PickupTaskResult(
            data = pickupTasks,
            totalRecords = pickupTasks.size
        )
    }

}