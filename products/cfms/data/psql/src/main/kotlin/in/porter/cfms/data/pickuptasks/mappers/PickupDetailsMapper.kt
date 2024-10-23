package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.pickuptasks.records.PickupDetailsRecord
import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails
import javax.inject.Inject

class PickupDetailsMapper @Inject constructor() {

    // Convert PickupDetailsRecord to domain PickupDetails entity
    fun toDomain(pickupDetailsRecord: PickupDetailsRecord): PickupDetails {
        return PickupDetails(
            pickupDetailsId = pickupDetailsRecord.pickupDetailsId,
            taskId = pickupDetailsRecord.taskId,
            orderId = pickupDetailsRecord.orderId,
            hlpId = pickupDetailsRecord.hlpId,
            franchiseId = pickupDetailsRecord.franchiseId,
            status = pickupDetailsRecord.status,
            createdAt = pickupDetailsRecord.createdAt,
            updatedAt = pickupDetailsRecord.updatedAt
        )
    }

    // Convert domain PickupDetails entity to PickupDetailsRecord for persistence
    fun toRecord(pickupDetails: PickupDetails): PickupDetailsRecord {
        return PickupDetailsRecord(
            pickupDetailsId = pickupDetails.pickupDetailsId,
            taskId = pickupDetails.taskId,
            orderId = pickupDetails.orderId,
            hlpId = pickupDetails.hlpId,
            franchiseId = pickupDetails.franchiseId,
            status = pickupDetails.status,
            createdAt = pickupDetails.createdAt,
            updatedAt = pickupDetails.updatedAt
        )
    }
}
