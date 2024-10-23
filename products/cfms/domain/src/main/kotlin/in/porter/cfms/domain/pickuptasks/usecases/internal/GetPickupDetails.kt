package `in`.porter.cfms.domain.pickuptasks.usecases.internal

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails
import `in`.porter.cfms.domain.pickuptasks.repos.PickupDetailsRepo
import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class GetPickupTask @Inject constructor(
    private val pickupDetailsRepo: PickupDetailsRepo
) : Traceable {
    suspend fun findTaskById(taskId: String): PickupDetails =
        trace() {
            pickupDetailsRepo.findTaskById(taskId)
                ?: throw CfmsException("Task with ID $taskId not found")
        }
}