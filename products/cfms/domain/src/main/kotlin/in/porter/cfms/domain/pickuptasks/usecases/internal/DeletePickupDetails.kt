package `in`.porter.cfms.domain.pickuptasks.usecases.internal

import `in`.porter.cfms.domain.pickuptasks.repos.PickupDetailsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeletePickupDetails @Inject constructor(
    private val pickupDetailsRepo: PickupDetailsRepo,
    private val getPickupDetails: GetPickupTask
) : Traceable {
    private val logger = LoggerFactory.getLogger(DeletePickupDetails::class.java)
    suspend fun invoke(taskId: String) {
        logger.info("Attempting to delete task with ID: $taskId")
        getPickupDetails.findTaskById(taskId)
        pickupDetailsRepo.deleteTaskById(taskId)
        logger.info("Task with ID $taskId successfully deleted")
    }
}
