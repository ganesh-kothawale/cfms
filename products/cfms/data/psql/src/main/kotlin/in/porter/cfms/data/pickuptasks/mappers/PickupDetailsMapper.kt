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
            hlpId = pickupDetailsRecord.hlpId,
            franchiseId = pickupDetailsRecord.franchiseId,
            createdAt = pickupDetailsRecord.createdAt,
            updatedAt = pickupDetailsRecord.updatedAt,
            orderImages = pickupDetailsRecord.orderImages,
            packageReceived = pickupDetailsRecord.packageReceived
        )
    }

    // Convert domain PickupDetails entity to PickupDetailsRecord for persistence
    fun toRecord(pickupDetails: PickupDetails): PickupDetailsRecord {
        return PickupDetailsRecord(
            pickupDetailsId = pickupDetails.pickupDetailsId,
            taskId = pickupDetails.taskId,
            hlpId = pickupDetails.hlpId,
            franchiseId = pickupDetails.franchiseId,
            createdAt = pickupDetails.createdAt,
            updatedAt = pickupDetails.updatedAt,
            orderImages = pickupDetails.orderImages,
            packageReceived = pickupDetails.packageReceived
        )
    }
}
