package `in`.porter.cfms.api.service.pickupTasks.mappers

import `in`.porter.cfms.api.models.pickupTasks.CreatePickupDetailsRequest
import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails
import java.time.Instant
import javax.inject.Inject

class CreatePickupDetailsRequestMapper @Inject constructor() {

    fun toDomain(request: CreatePickupDetailsRequest, generatedPickupDetailsId: String): PickupDetails {
        return PickupDetails(
            pickupDetailsId = generatedPickupDetailsId,
            taskId = request.taskId,
            orderId = request.orderId,
            hlpId = request.hlpId,
            franchiseId = request.franchiseId,
            status = request.status,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
